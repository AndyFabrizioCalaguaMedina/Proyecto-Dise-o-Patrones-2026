package com.logistica.patterns.observer;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;

/**
 * Patrón GoF: Observer - Interfaz Observador.
 * Define la acción reactiva a ejecutarse cuando ocurre un evento en el ciclo de vida del paquete.
 */
public interface ITrackingObserver {
    void onCambioEstado(Envio envio, EstadoEnvio estadoAnterior, EstadoEnvio nuevoEstado, String observacion);
}
