package com.logistica.patterns.factory;

/**
 * Patrón GoF: Factory Method - Producto Abstracto.
 * Define las operaciones que todo servicio logístico debe implementar.
 */
public interface ServicioEnvio {
    String getNombreServicio();
    int getTiempoEntregaHoras();
    double calcularTarifa(double pesoKg, double distanciaKm);
    void despachar(String codigoTracking, String origen, String destino);
}
