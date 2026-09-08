package com.logistica.model;

/**
 * Estados del ciclo de vida de un envío en la plataforma logística.
 */
public enum EstadoEnvio {
    REGISTRADO("Registrado en sistema"),
    RECOGIDO("Paquete recogido en origen"),
    EN_TRANSITO("En tránsito a centro de consolidación"),
    CLASIFICADO("Clasificado en Hub logístico"),
    EN_RUTA("En unidad de reparto hacia destino"),
    ENTREGADO("Entregado con éxito al destinatario"),
    OBSERVADO("Observado por incidencia o dirección no encontrada"),
    REPROGRAMADO("Reprogramado para nueva visita"),
    DEVUELTO("Devuelto a remitente o almacén");

    private final String descripcion;

    EstadoEnvio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
