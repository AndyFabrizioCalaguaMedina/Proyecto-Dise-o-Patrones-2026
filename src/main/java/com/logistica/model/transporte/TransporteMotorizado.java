package com.logistica.model.transporte;

/**
 * Subclase intermedia para vehículos motorizados que requieren combustible/energía.
 */
public abstract class TransporteMotorizado extends Transporte {

    protected double nivelCombustibleLitros;

    public TransporteMotorizado(String identificador, double capacidadCargaKg, double combustibleInicial) {
        super(identificador, capacidadCargaKg);
        this.nivelCombustibleLitros = combustibleInicial;
    }

    public void recargarCombustible(double litros) {
        this.nivelCombustibleLitros += litros;
        System.out.println("[Transporte Motorizado] " + identificador + " recargó " + litros + "L de combustible.");
    }

    public double getNivelCombustibleLitros() {
        return nivelCombustibleLitros;
    }
}
