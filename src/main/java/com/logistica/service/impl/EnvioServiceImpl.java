package com.logistica.service.impl;

import com.logistica.dao.IEnvioDAO;
import com.logistica.dto.EnvioRegistroDTO;
import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.patterns.observer.*;
import com.logistica.service.IEnvioService;
import com.logistica.service.INotificacionService;
import com.logistica.service.ITarifaCalculatorService;
import com.logistica.util.GeneradorCodigo;

import java.util.*;

/**
 * Implementación del servicio de Envíos:
 * Demuestra SRP, DIP y coordina la trazabilidad reactiva con el Patrón Observer.
 */
public class EnvioServiceImpl implements IEnvioService {

    private final IEnvioDAO envioDAO;
    private final ITarifaCalculatorService tarifaCalculator;
    private final INotificacionService notificador;
    private final Map<String, EnvioObservable> observables = new HashMap<>();

    // Inyección de dependencias a través del constructor (DIP)
    public EnvioServiceImpl(IEnvioDAO envioDAO, ITarifaCalculatorService tarifaCalculator, INotificacionService notificador) {
        this.envioDAO = envioDAO;
        this.tarifaCalculator = tarifaCalculator;
        this.notificador = notificador;
    }

    @Override
    public RespuestaOperacionDTO registrarEnvio(EnvioRegistroDTO dto) {
        // 1. Delegar cálculo de tarifa (SRP / OCP)
        double costo = tarifaCalculator.calcularTarifa(dto.getTipoServicio(), dto.getPesoKg(), dto.getDistanciaKm());

        // 2. Generar tracking único
        String tracking = GeneradorCodigo.generarTracking(dto.getTipoServicio());

        // 3. Crear entidad de dominio
        Envio envio = new Envio(
                tracking,
                dto.getTipoServicio(),
                dto.getIdRemitente(),
                dto.getNombreDestinatario(),
                dto.getDireccionDestino(),
                dto.getTelefonoDestinatario(),
                dto.getEmailDestinatario(),
                dto.getPesoKg(),
                costo
        );

        // 4. Persistir mediante DAO (DIP)
        boolean guardado = envioDAO.insertar(envio);
        if (!guardado) {
            return new RespuestaOperacionDTO(false, "No se pudo registrar el envío en la base de datos.");
        }

        // 5. Configurar Sujeto Observable (Patrón Observer)
        EnvioObservable observable = new EnvioObservable(envio);
        observable.suscribir(new DestinatarioNotificacionObserver());
        observable.suscribir(new CentroMonitoreoObserver());
        observable.suscribir(new AuditoriaTrackingObserver());
        observables.put(tracking, observable);

        // 6. Notificar al cliente vía canal desacoplado (DIP)
        notificador.enviarNotificacion(dto.getEmailDestinatario(), "Guía de Envío Generada",
                "Su paquete ha sido registrado con código de seguimiento: " + tracking + ". Total a pagar: S/ " + String.format("%.2f", costo));

        return new RespuestaOperacionDTO(true, "Envío registrado con éxito.", envio);
    }

    @Override
    public Optional<Envio> consultarTracking(String codigoTracking) {
        return envioDAO.buscarPorTracking(codigoTracking);
    }

    @Override
    public List<Envio> listarTodos() {
        return envioDAO.listarTodos();
    }

    @Override
    public RespuestaOperacionDTO actualizarEstadoEnvio(String codigoTracking, EstadoEnvio nuevoEstado, String ubicacion, String observacion) {
        Optional<Envio> optEnvio = envioDAO.buscarPorTracking(codigoTracking);
        if (optEnvio.isEmpty()) {
            return new RespuestaOperacionDTO(false, "El envío con código " + codigoTracking + " no existe.");
        }

        // 1. Disparar notificaciones reactivas a través de Observer
        EnvioObservable observable = observables.get(codigoTracking);
        if (observable == null) {
            observable = new EnvioObservable(optEnvio.get());
            observable.suscribir(new DestinatarioNotificacionObserver());
            observable.suscribir(new CentroMonitoreoObserver());
            observable.suscribir(new AuditoriaTrackingObserver());
            observables.put(codigoTracking, observable);
        }

        observable.transicionarEstado(nuevoEstado, observacion);

        // 2. Actualizar en Base de Datos MySQL
        envioDAO.actualizarEstado(codigoTracking, nuevoEstado, ubicacion, observacion);

        return new RespuestaOperacionDTO(true, "Estado de envío actualizado a " + nuevoEstado, optEnvio.get());
    }
}
