package com.logistica.patterns.abstractfactory.urbana;

import com.logistica.patterns.abstractfactory.ITransporteLogistico;

/**
 * Producto Concreto (Familia Urbana): Moto de reparto para entregas locales.
 */
public class MotoReparto implements ITransporteLogistico {
    @Override
    public String getTipo() { return "Moto de Reparto Urbana"; }

    @Override
    public double getCapacidadKg() { return 20.0; }

    @Override
    public void asignarRuta(String origen, String destino) {
        System.out.printf("  [MOTO] Ruta asignada: %s → %s (máx. 20 kg, radio 15 km)%n", origen, destino);
    }
}