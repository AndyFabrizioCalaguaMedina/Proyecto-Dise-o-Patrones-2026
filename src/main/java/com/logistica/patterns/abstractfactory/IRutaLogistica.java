package com.logistica.patterns.abstractfactory;

/**
 * Patrón GoF: ABSTRACT FACTORY — Producto Abstracto: Ruta.
 * Define el contrato para la planificación de rutas logísticas.
 */
public interface IRutaLogistica {
    String getTipoRuta();
    double getDistanciaEstimadaKm();
    void planificar(String origen, String destino);
}