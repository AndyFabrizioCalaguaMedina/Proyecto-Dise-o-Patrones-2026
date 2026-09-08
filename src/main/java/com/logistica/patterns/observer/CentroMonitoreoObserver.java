package com.logistica.patterns.observer;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;

/**
 * Observador Concreto: Actualiza el panel de telemetría y enciende alarmas operativas.
 */
public class CentroMonitoreoObserver implements ITrackingObserver {

    @Override
    public void onCambioEstado(Envio envio, EstadoEnvio estadoAnterior, EstadoEnvio nuevoEstado, String observacion) {
        if (nuevoEstado == EstadoEnvio.OBSERVADO) {
            System.err.println("   [Observer-CentroMonitoreo] [ALERTA ROJA] Incidencia en Guía " + envio.getCodigoTracking() + " -> " + observacion);
        } else {
            System.out.println("   [Observer-CentroMonitoreo] Dashboard actualizado: Guía " + envio.getCodigoTracking() + " está " + nuevoEstado);
        }
    }
}
