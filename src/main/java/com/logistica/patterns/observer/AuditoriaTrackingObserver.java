package com.logistica.patterns.observer;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Observador Concreto: Bitácora inmutable de trazabilidad para auditorías y SLA.
 */
public class AuditoriaTrackingObserver implements ITrackingObserver {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onCambioEstado(Envio envio, EstadoEnvio estadoAnterior, EstadoEnvio nuevoEstado, String observacion) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("   [Observer-Auditoría] [" + timestamp + "] [LOG] Tracking: " + envio.getCodigoTracking()
                + " | " + estadoAnterior + " -> " + nuevoEstado + " | Obs: " + observacion);
    }
}
