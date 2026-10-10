package com.logistica.patterns.composite;

/**
 * Patrón GoF: COMPOSITE — Hoja (Leaf).
 *
 * Representa una parada individual de entrega en la ruta.
 * Es el elemento más simple, sin hijos.
 */
public class ParadaEntrega implements IComponenteRuta {

    private final String direccion;
    private final String codigoEnvio;
    private final double distanciaKm;

    public ParadaEntrega(String direccion, String codigoEnvio, double distanciaKm) {
        this.direccion = direccion;
        this.codigoEnvio = codigoEnvio;
        this.distanciaKm = distanciaKm;
    }

    @Override
    public double getDistanciaTotal() {
        return distanciaKm;
    }

    @Override
    public void mostrarDetalle(int nivel) {
        String indent = "  ".repeat(nivel);
        System.out.printf("%s📍 Parada: %s [Envío: %s] — %.1f km%n",
                indent, direccion, codigoEnvio, distanciaKm);
    }

    public String getDireccion() { return direccion; }
    public String getCodigoEnvio() { return codigoEnvio; }
}