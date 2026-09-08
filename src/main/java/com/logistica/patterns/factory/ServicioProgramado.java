package com.logistica.patterns.factory;

/**
 * Producto Concreto: Servicio de Mensajería Programada.
 */
public class ServicioProgramado implements ServicioEnvio {

    @Override
    public String getNombreServicio() {
        return "Mensajería Programada con Franja Horaria";
    }

    @Override
    public int getTiempoEntregaHoras() {
        return 24;
    }

    @Override
    public double calcularTarifa(double pesoKg, double distanciaKm) {
        return 18.00 + (pesoKg * 4.00) + (distanciaKm * 0.30);
    }

    @Override
    public void despachar(String codigoTracking, String origen, String destino) {
        System.out.println("[Factory-Programado] Guía " + codigoTracking + " reservada para franja agendada entre " + origen + " y " + destino);
    }
}
