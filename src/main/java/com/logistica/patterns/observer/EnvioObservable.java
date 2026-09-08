package com.logistica.patterns.observer;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón GoF: Observer - Sujeto Notificador (Subject).
 * Administra suscriptores y despacha notificaciones ante cualquier transición de estado.
 */
public class EnvioObservable {

    private final Envio envio;
    private final List<ITrackingObserver> observadores = new ArrayList<>();

    public EnvioObservable(Envio envio) {
        this.envio = envio;
    }

    public void suscribir(ITrackingObserver observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(ITrackingObserver observador) {
        observadores.remove(observador);
    }

    public void transicionarEstado(EstadoEnvio nuevoEstado, String observacion) {
        EstadoEnvio anterior = envio.getEstadoActual();
        envio.setEstadoActual(nuevoEstado);
        System.out.println("\n[EVENTO LOGÍSTICO] Transición de Envio " + envio.getCodigoTracking() + ": " + anterior + " -> " + nuevoEstado);
        notificar(anterior, nuevoEstado, observacion);
    }

    private void notificar(EstadoEnvio anterior, EstadoEnvio nuevo, String observacion) {
        for (ITrackingObserver obs : observadores) {
            obs.onCambioEstado(envio, anterior, nuevo, observacion);
        }
    }

    public Envio getEnvio() {
        return envio;
    }
}
