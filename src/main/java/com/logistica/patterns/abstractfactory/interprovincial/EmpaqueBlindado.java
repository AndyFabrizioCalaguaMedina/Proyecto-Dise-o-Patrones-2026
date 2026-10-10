package com.logistica.patterns.abstractfactory.interprovincial;

import com.logistica.patterns.abstractfactory.IEmpaque;

/**
 * Producto Concreto (Familia Interprovincial): Empaque blindado para larga distancia.
 */
public class EmpaqueBlindado implements IEmpaque {
    @Override
    public String getTipoEmpaque() { return "Empaque Blindado (protección anti-golpes)"; }

    @Override
    public double getCostoAdicional() { return 15.00; }

    @Override
    public void empacar(String codigoTracking) {
        System.out.printf("  [EMPAQUE BLINDADO] Paquete %s protegido con doble capa + relleno. Costo: S/ 15.00%n", codigoTracking);
    }
}