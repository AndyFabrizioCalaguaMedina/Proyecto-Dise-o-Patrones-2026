package com.logistica.service.impl;

import com.logistica.service.INotificacionService;

/**
 * Canal de Notificación vía Correo Electrónico (DIP & OCP).
 */
public class NotificacionEmailService implements INotificacionService {

    @Override
    public void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        System.out.println("[CANAL EMAIL] Para: <" + destinatario + "> | Asunto: [" + asunto + "] -> " + mensaje);
    }

    @Override
    public String getNombreCanal() {
        return "EMAIL";
    }
}
