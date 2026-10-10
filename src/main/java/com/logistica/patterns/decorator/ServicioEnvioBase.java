package com.logistica.patterns.decorator;

/**
 * Patrón GoF: DECORATOR — Componente Concreto.
 *
 * Representa un servicio de envío base sin extras.
 * Los Decoradores envolverán esta clase para agregar funcionalidades.
 */
public class ServicioEnvioBase implements IServicioEnvioDecorable {

    private final String tipoServicio;
    private final double costoBase;

    public ServicioEnvioBase(String tipoServicio, double costoBase) {
        this.tipoServicio = tipoServicio;
        this.costoBase = costoBase;
    }

    @Override
    public double calcularCosto() {
        return costoBase;
    }

    @Override
    public String getDescripcion() {
        return "Servicio " + tipoServicio;
    }
}