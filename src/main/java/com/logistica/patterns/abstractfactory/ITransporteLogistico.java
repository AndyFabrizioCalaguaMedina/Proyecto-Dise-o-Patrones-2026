package com.logistica.patterns.abstractfactory;

/**
 * Patrón GoF: ABSTRACT FACTORY — Producto Abstracto: Transporte.
 * Define el contrato para cualquier unidad de transporte logístico.
 */
public interface ITransporteLogistico {
    String getTipo();
    double getCapacidadKg();
    void asignarRuta(String origen, String destino);
}