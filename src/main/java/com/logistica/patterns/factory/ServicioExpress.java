package com.logistica.patterns.factory;

/**
 * Producto Concreto: Servicio de Entrega Express.
 */
public class ServicioExpress implements ServicioEnvio {

    @Override
    public String getNombreServicio() {
        return "Entrega Express Urgente (< 12 Horas)";
    }

    @Override
    public int getTiempoEntregaHoras() {
        return 12;
    }

    @Override
    public double calcularTarifa(double pesoKg, double distanciaKm) {
        return 25.00 + (pesoKg * 5.00) + (distanciaKm * 0.50);
    }

    @Override
    public void despachar(String codigoTracking, String origen, String destino) {
        System.out.println("[Factory-Express] Guía Prioritaria " + codigoTracking + " despachada con ruta directa sin escalas de " + origen + " a " + destino);
    }
}
