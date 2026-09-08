package com.logistica.patterns.observer;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;

/**
 * Observador Concreto: Notifica al destinatario final según el estado.
 */
public class DestinatarioNotificacionObserver implements ITrackingObserver {

    @Override
    public void onCambioEstado(Envio envio, EstadoEnvio estadoAnterior, EstadoEnvio nuevoEstado, String observacion) {
        String mensaje;
        switch (nuevoEstado) {
            case EN_RUTA:
                mensaje = "¡Tu pedido está en camino hacia tu dirección: " + envio.getDireccionDestino() + "!";
                break;
            case ENTREGADO:
                mensaje = "¡Tu paquete fue entregado con éxito! Gracias por tu preferencia.";
                break;
            case OBSERVADO:
                mensaje = "Incidencia en tu entrega: " + observacion + ". Nos comunicaremos a tu teléfono " + envio.getTelefonoDestinatario();
                break;
            default:
                mensaje = "Actualización: " + nuevoEstado.getDescripcion();
                break;
        }
        System.out.println("   [Observer-Destinatario] Notificación enviada a " + envio.getNombreDestinatario()
                + " (" + envio.getEmailDestinatario() + "): \"" + mensaje + "\"");
    }
}
