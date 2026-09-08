package com.logistica.patterns.factory;

/**
 * Producto Concreto: Servicio de Envío Estándar Consolidado.
 */
public class ServicioEstandar implements ServicioEnvio {

    @Override
    public String getNombreServicio() {
        return "Envío Estándar Consolidado (24-48 Horas)";
    }

    @Override
    public int getTiempoEntregaHoras() {
        return 48;
    }

    @Override
    public double calcularTarifa(double pesoKg, double distanciaKm) {
        return 12.00 + (pesoKg * 3.00) + (distanciaKm * 0.20);
    }

    @Override
    public void despachar(String codigoTracking, String origen, String destino) {
        System.out.println("[Factory-Estándar] Guía " + codigoTracking + " asignada a centro de consolidación regular de " + origen + " a " + destino);
    }
}
