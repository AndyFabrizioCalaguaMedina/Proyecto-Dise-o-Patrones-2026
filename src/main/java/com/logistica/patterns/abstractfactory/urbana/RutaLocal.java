package com.logistica.patterns.abstractfactory.urbana;

import com.logistica.patterns.abstractfactory.IRutaLogistica;

/**
 * Producto Concreto (Familia Urbana): Ruta local dentro de la ciudad.
 */
public class RutaLocal implements IRutaLogistica {
    @Override
    public String getTipoRuta() { return "Ruta Local Urbana"; }

    @Override
    public double getDistanciaEstimadaKm() { return 12.0; }

    @Override
    public void planificar(String origen, String destino) {
        System.out.printf("  [RUTA LOCAL] Planificada: %s → %s (~12 km, 45 min estimado)%n", origen, destino);
    }
}