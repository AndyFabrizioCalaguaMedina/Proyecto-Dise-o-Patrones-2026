package com.logistica.service.impl;

import com.logistica.service.ITarifaCalculatorService;

/**
 * Implementación de cálculo de tarifas cumpliendo SRP y OCP.
 */
public class TarifaCalculatorServiceImpl implements ITarifaCalculatorService {

    @Override
    public double calcularTarifa(String tipoServicio, double pesoKg, double distanciaKm) {
        double base;
        double porKg;

        switch (tipoServicio.toUpperCase()) {
            case "EXPRESS":
                base = 25.00;
                porKg = 5.00;
                break;
            case "PROGRAMADO":
                base = 18.00;
                porKg = 4.00;
                break;
            case "INTERPROVINCIAL":
                base = 35.00;
                porKg = 7.00;
                break;
            case "ESTANDAR":
            default:
                base = 12.00;
                porKg = 3.00;
                break;
        }

        double recargoDistancia = (distanciaKm > 10.0) ? (distanciaKm - 10.0) * 0.50 : 0.0;
        return base + (pesoKg * porKg) + recargoDistancia;
    }
}
