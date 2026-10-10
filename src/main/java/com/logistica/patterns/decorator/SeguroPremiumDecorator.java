package com.logistica.patterns.decorator;

/**
 * Patrón GoF: DECORATOR — Decorador Concreto: Seguro Premium.
 *
 * Justificación: Los clientes pueden contratar un seguro premium
 * que cubre el valor total del contenido del paquete. Se agrega
 * dinámicamente sin modificar la clase base (OCP).
 */
public class SeguroPremiumDecorator implements IServicioEnvioDecorable {

    private final IServicioEnvioDecorable servicioDecorado;
    private final double montoAsegurado;

    public SeguroPremiumDecorator(IServicioEnvioDecorable servicioDecorado, double montoAsegurado) {
        this.servicioDecorado = servicioDecorado;
        this.montoAsegurado = montoAsegurado;
    }

    @Override
    public double calcularCosto() {
        // Prima del seguro: 5% del valor asegurado
        double primaSeguro = montoAsegurado * 0.05;
        return servicioDecorado.calcularCosto() + primaSeguro;
    }

    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion()
                + String.format(" + Seguro Premium (S/ %.2f asegurado)", montoAsegurado);
    }
}