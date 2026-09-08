package com.logistica.dto;

import com.logistica.model.EstadoEnvio;
import java.time.LocalDateTime;

/**
 * DTO para la consulta y reporte de eventos de trazabilidad de un paquete.
 */
public class TrackingEventoDTO {
    private String codigoTracking;
    private EstadoEnvio estado;
    private String ubicacion;
    private String observacion;
    private LocalDateTime fechaHora;

    public TrackingEventoDTO(String codigoTracking, EstadoEnvio estado, String ubicacion, String observacion) {
        this.codigoTracking = codigoTracking;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.observacion = observacion;
        this.fechaHora = LocalDateTime.now();
    }

    public String getCodigoTracking() { return codigoTracking; }
    public EstadoEnvio getEstado() { return estado; }
    public String getUbicacion() { return ubicacion; }
    public String getObservacion() { return observacion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}
