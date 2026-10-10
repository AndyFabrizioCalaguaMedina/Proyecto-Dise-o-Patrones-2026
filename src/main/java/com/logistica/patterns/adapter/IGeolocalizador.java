package com.logistica.patterns.adapter;

/**
 * Patrón GoF: ADAPTER (Estructural) — Interfaz Target del dominio.
 *
 * Define el contrato que el sistema logístico espera para obtener
 * coordenadas geográficas de cualquier dirección.
 *
 * Principio SOLID reforzado: DIP — el sistema depende de esta abstracción,
 * no de APIs externas concretas.
 */
public interface IGeolocalizador {
    /**
     * Obtiene las coordenadas (lat, lon) de una dirección.
     * @param direccion Dirección textual a geolocalizar
     * @return Array de double [latitud, longitud]
     */
    double[] obtenerCoordenadas(String direccion);

    /**
     * Calcula la distancia en kilómetros entre dos direcciones.
     */
    double calcularDistancia(String origen, String destino);
}