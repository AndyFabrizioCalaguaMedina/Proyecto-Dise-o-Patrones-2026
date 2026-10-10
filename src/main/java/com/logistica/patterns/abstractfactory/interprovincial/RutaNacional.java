package com.logistica.patterns.abstractfactory.interprovincial;

import com.logistica.patterns.abstractfactory.IRutaLogistica;

/**
 * Producto Concreto (Familia Interprovincial): Ruta nacional de larga distancia.
 */
public class RutaNacional implements IRutaLogistica {
    @Override
    public String getTipoRuta() { return "Ruta Nacional Interprovincial"; }

    @Override
    public double getDistanciaEstimadaKm() { return 450.0; }

    @Override
    public void planificar(String origen, String destino) {
        System.out.printf("  [RUTA NACIONAL] Planificada: %s → %s (~450 km, 8-12 horas estimado)%n", origen, destino);
    }
}