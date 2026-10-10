package com.logistica.patterns.decorator;

/**
 * Patrón GoF: DECORATOR — Decorador Concreto: Rastreo SMS en tiempo real.
 *
 * Agrega notificaciones SMS en cada cambio de estado del envío.
 * Cobro adicional por envío de SMS automatizado.
 */
public class RastreoSMSDecorator implements IServicioEnvioDecorable {

    private final IServicioEnvioDecorable servicioDecorado;
    private static final double COSTO_SMS = 3.00;

    public RastreoSMSDecorator(IServicioEnvioDecorable servicioDecorado) {
        this.servicioDecorado = servicioDecorado;
    }

    @Override
    public double calcularCosto() {
        return servicioDecorado.calcularCosto() + COSTO_SMS;
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion() + " + Rastreo SMS en tiempo real";
    }
}