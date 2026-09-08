package com.logistica.patterns.factory;

/**
 * Creador Concreto para Envíos Express.
 */
public class ExpressFactory extends EnvioFactory {
    @Override
    public ServicioEnvio crearServicio() {
        return new ServicioExpress();
    }
}
