package com.logistica.dto;

/**
 * DTO genérico para respuestas de operaciones del sistema.
 */
public class RespuestaOperacionDTO {
    private boolean exito;
    private String mensaje;
    private Object data;

    public RespuestaOperacionDTO(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public RespuestaOperacionDTO(boolean exito, String mensaje, Object data) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.data = data;
    }

    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
    public Object getData() { return data; }
}
