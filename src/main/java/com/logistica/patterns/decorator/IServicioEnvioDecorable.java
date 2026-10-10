package com.logistica.patterns.decorator;

/**
 * Patrón GoF: DECORATOR (Estructural) — Componente (Interfaz base).
 *
 * Define el contrato que comparten tanto el componente concreto
 * como todos los decoradores.
 */
public interface IServicioEnvioDecorable {
    double calcularCosto();
    String getDescripcion();
}