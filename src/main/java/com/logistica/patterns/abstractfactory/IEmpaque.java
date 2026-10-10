package com.logistica.patterns.abstractfactory;

/**
 * Patrón GoF: ABSTRACT FACTORY — Producto Abstracto: Empaque.
 * Define el contrato para los materiales de embalaje por zona.
 */
public interface IEmpaque {
    String getTipoEmpaque();
    double getCostoAdicional();
    void empacar(String codigoTracking);
}