package com.logistica.patterns.factory;

/**
 * Creador Concreto para Envíos Estándar.
 */
public class EstandarFactory extends EnvioFactory {
    @Override
    public ServicioEnvio crearServicio() {
        return new ServicioEstandar();
    }
}
