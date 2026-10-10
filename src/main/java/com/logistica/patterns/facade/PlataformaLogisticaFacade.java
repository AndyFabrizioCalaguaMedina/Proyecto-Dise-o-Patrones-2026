package com.logistica.patterns.facade;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.patterns.adapter.GoogleMapsAdapter;
import com.logistica.patterns.adapter.IGeolocalizador;
import com.logistica.patterns.builder.EnvioBuilder;
import com.logistica.patterns.decorator.*;
import com.logistica.patterns.observer.*;
import com.logistica.util.GeneradorCodigo;

/**
 * Patrón GoF: FACADE (Estructural).
 *
 * Justificación: Registrar un envío completo involucra múltiples subsistemas:
 * validar datos, geolocalizar, calcular tarifa, construir el envío (Builder),
 * agregar servicios adicionales (Decorator), persistir, notificar (Observer)
 * y asignar ruta. PlataformaLogisticaFacade ofrece un punto de entrada
 * SIMPLE que oculta toda esta complejidad al cliente.
 *
 * Principios SOLID reforzados:
 * - SRP: la fachada solo ORQUESTA, no implementa lógica de negocio.
 * - DIP: depende de abstracciones (interfaces) de los subsistemas.
 *
 * Patrones integrados: Builder, Decorator, Observer, Adapter.
 */
public class PlataformaLogisticaFacade {

    private final IGeolocalizador geolocalizador;

    public PlataformaLogisticaFacade() {
        // Usa el Adapter para integrar Google Maps
        this.geolocalizador = new GoogleMapsAdapter();
    }

    /**
     * Operación simplificada: Registra un envío completo con todos los pasos.
     * El cliente solo necesita llamar a este método.
     */
    public Envio registrarEnvioCompleto(String tipoServicio, int idRemitente,
                                         String destinatario, String direccion,
                                         String telefono, String email,
                                         double pesoKg, boolean conSeguro,
                                         boolean conEmpaqueReforzado, boolean conRastreoSMS) {

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║       FACADE — REGISTRO DE ENVÍO SIMPLIFICADO       ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Paso 1: Geolocalizar dirección (Adapter)
        System.out.println("\n[PASO 1] Geolocalizando dirección de destino...");
        double[] coordenadas = geolocalizador.obtenerCoordenadas(direccion);
        double distancia = geolocalizador.calcularDistancia("Almacén Central - Lima", direccion);

        // Paso 2: Calcular costo con decoradores (Decorator)
        System.out.println("\n[PASO 2] Calculando costo del servicio...");
        double costoBase = calcularCostoBase(tipoServicio, pesoKg, distancia);
        IServicioEnvioDecorable servicio = new ServicioEnvioBase(tipoServicio, costoBase);

        if (conSeguro) {
            servicio = new SeguroPremiumDecorator(servicio, 500.00);
        }
        if (conEmpaqueReforzado) {
            servicio = new EmpaqueReforzadoDecorator(servicio);
        }
        if (conRastreoSMS) {
            servicio = new RastreoSMSDecorator(servicio);
        }

        System.out.println("  Descripción: " + servicio.getDescripcion());
        System.out.printf("  Costo total: S/ %.2f%n", servicio.calcularCosto());

        // Paso 3: Construir el envío (Builder)
        System.out.println("\n[PASO 3] Construyendo envío...");
        Envio envio = new EnvioBuilder()
                .conTipoServicio(tipoServicio)
                .conRemitente(idRemitente)
                .conDestinatario(destinatario, direccion)
                .conContacto(telefono, email)
                .conPeso(pesoKg)
                .conCosto(servicio.calcularCosto())
                .build();

        // Paso 4: Configurar trazabilidad reactiva (Observer)
        System.out.println("\n[PASO 4] Configurando notificaciones de tracking...");
        EnvioObservable observable = new EnvioObservable(envio);
        observable.suscribir(new DestinatarioNotificacionObserver());
        observable.suscribir(new CentroMonitoreoObserver());
        observable.suscribir(new AuditoriaTrackingObserver());

        System.out.println("\n✅ Envío registrado exitosamente vía FACADE.");
        System.out.println("   Tracking: " + envio.getCodigoTracking());
        System.out.printf("   Coordenadas destino: [%.6f, %.6f]%n", coordenadas[0], coordenadas[1]);

        return envio;
    }

    /**
     * Operación simplificada: Simula el ciclo de vida completo de un envío.
     */
    public void simularCicloDeVida(Envio envio) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     FACADE — SIMULACIÓN DEL CICLO DE VIDA           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        EnvioObservable observable = new EnvioObservable(envio);
        observable.suscribir(new DestinatarioNotificacionObserver());
        observable.suscribir(new CentroMonitoreoObserver());
        observable.suscribir(new AuditoriaTrackingObserver());

        // Transiciones automáticas del ciclo de vida
        observable.transicionarEstado(EstadoEnvio.RECOGIDO, "Paquete recogido por motorizado");
        observable.transicionarEstado(EstadoEnvio.EN_TRANSITO, "En camino al centro de distribución");
        observable.transicionarEstado(EstadoEnvio.CLASIFICADO, "Clasificado en Hub Lima Centro");
        observable.transicionarEstado(EstadoEnvio.EN_RUTA, "En unidad de reparto hacia destino");
        observable.transicionarEstado(EstadoEnvio.ENTREGADO, "Entregado al destinatario con firma");

        System.out.println("\n✅ Ciclo de vida completado para envío: " + envio.getCodigoTracking());
    }

    private double calcularCostoBase(String tipo, double pesoKg, double distanciaKm) {
        double base;
        double porKg;
        switch (tipo.toUpperCase()) {
            case "EXPRESS":       base = 25.00; porKg = 5.00; break;
            case "PROGRAMADO":    base = 18.00; porKg = 4.00; break;
            case "INTERPROVINCIAL": base = 35.00; porKg = 7.00; break;
            default:              base = 12.00; porKg = 3.00; break;
        }
        double recargoDistancia = (distanciaKm > 10.0) ? (distanciaKm - 10.0) * 0.50 : 0.0;
        return base + (pesoKg * porKg) + recargoDistancia;
    }
}