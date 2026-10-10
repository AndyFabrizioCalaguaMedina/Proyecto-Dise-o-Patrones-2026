package com.logistica.patterns.abstractfactory.interprovincial;

import com.logistica.patterns.abstractfactory.ITransporteLogistico;

/**
 * Producto Concreto (Familia Interprovincial): Camión de carga pesada.
 */
public class CamionCarga implements ITransporteLogistico {
    @Override
    public String getTipo() { return "Camión de Carga Interprovincial"; }

    @Override
    public double getCapacidadKg() { return 5000.0; }

    @Override
    public void asignarRuta(String origen, String destino) {
        System.out.printf("  [CAMIÓN] Ruta interprovincial: %s → %s (máx. 5000 kg, larga distancia)%n", origen, destino);
    }
}