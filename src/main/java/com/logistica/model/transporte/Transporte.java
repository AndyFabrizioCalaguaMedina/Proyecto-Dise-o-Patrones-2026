package com.logistica.model.transporte;

/**
 * Base de la jerarquía de vehículos y unidades de distribución (Demuestra LSP).
 * Define operaciones comunes a cualquier medio de transporte.
 */
public abstract class Transporte {

    protected String identificador;
    protected double capacidadCargaKg;

    public Transporte(String identificador, double capacidadCargaKg) {
        this.identificador = identificador;
        this.capacidadCargaKg = capacidadCargaKg;
    }

    public abstract void iniciarJornada(String sector);
    public abstract void trasladarPaquete(String origen, String destino);

    public String getIdentificador() {
        return identificador;
    }

    public double getCapacidadCargaKg() {
        return capacidadCargaKg;
    }
}
