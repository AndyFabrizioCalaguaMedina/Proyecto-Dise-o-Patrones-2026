package com.logistica.service;

/**
 * SRP & OCP: Contrato para el cálculo desacoplado de tarifas.
 */
public interface ITarifaCalculatorService {
    double calcularTarifa(String tipoServicio, double pesoKg, double distanciaKm);
}
