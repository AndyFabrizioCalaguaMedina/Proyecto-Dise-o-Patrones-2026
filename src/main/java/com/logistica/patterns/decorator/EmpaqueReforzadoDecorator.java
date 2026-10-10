package com.logistica.patterns.decorator;

/**
 * Patrón GoF: DECORATOR — Decorador Concreto: Empaque Reforzado.
 *
 * Agrega protección adicional con material anti-golpes para
 * artículos frágiles o de alto valor.
 */
public class EmpaqueReforzadoDecorator implements IServicioEnvioDecorable {

    private final IServicioEnvioDecorable servicioDecorado;
    private static final double COSTO_EMPAQUE = 8.50;

    public EmpaqueReforzadoDecorator(IServicioEnvioDecorable servicioDecorado) {
        this.servicioDecorado = servicioDecorado;
    }

    @Override
    public double calcularCosto() {
        return servicioDecorado.calcularCosto() + COSTO_EMPAQUE;
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion() + " + Empaque Reforzado (anti-golpes)";
    }
}