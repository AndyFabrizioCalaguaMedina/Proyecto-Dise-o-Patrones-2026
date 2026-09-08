package com.logistica.service.impl;

import com.logistica.service.INotificacionService;

/**
 * Canal de Notificación vía WhatsApp (DIP & OCP).
 */
public class NotificacionWhatsAppService implements INotificacionService {

    @Override
    public void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        System.out.println("[CANAL WHATSAPP] Destino: " + destinatario + " | Mensaje: " + mensaje);
    }

    @Override
    public String getNombreCanal() {
        return "WHATSAPP";
    }
}
