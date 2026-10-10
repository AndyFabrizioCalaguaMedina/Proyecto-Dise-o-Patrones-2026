package com.logistica.patterns.abstractfactory.urbana;

import com.logistica.patterns.abstractfactory.IEmpaque;

/**
 * Producto Concreto (Familia Urbana): Empaque estándar para paquetes locales.
 */
public class EmpaqueEstandar implements IEmpaque {
    @Override
    public String getTipoEmpaque() { return "Empaque Estándar (caja simple)"; }

    @Override
    public double getCostoAdicional() { return 2.50; }

    @Override
    public void empacar(String codigoTracking) {
        System.out.printf("  [EMPAQUE] Paquete %s embalado con caja estándar. Costo: S/ 2.50%n", codigoTracking);
    }
}