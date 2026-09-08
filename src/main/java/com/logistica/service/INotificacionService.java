package com.logistica.service;

/**
 * DIP & OCP: Contrato para canales de notificación desacoplados.
 */
public interface INotificacionService {
    void enviarNotificacion(String destinatario, String asunto, String mensaje);
    String getNombreCanal();
}
