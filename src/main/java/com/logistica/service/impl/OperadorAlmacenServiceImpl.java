package com.logistica.service.impl;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.service.IOperadorAlmacenService;

/**
 * ISP: Implementación de servicios para Operadores de Almacén.
 */
public class OperadorAlmacenServiceImpl implements IOperadorAlmacenService {

    private final String nombreOperador;

    public OperadorAlmacenServiceImpl(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    @Override
    public void recepcionarPaquete(Envio envio) {
        envio.setEstadoActual(EstadoEnvio.RECOGIDO);
        System.out.println("[Almacén] Operador " + nombreOperador + " recepcionó paquete " + envio.getCodigoTracking());
    }

    @Override
    public void clasificarPaquete(Envio envio, String sectorBahia) {
        envio.setEstadoActual(EstadoEnvio.CLASIFICADO);
        System.out.println("[Almacén] Operador " + nombreOperador + " clasificó paquete " + envio.getCodigoTracking() + " en: " + sectorBahia);
    }

    @Override
    public void consolidarLote(String idContenedor) {
        System.out.println("[Almacén] Contenedor " + idContenedor + " consolidado para despacho masivo.");
    }
}
