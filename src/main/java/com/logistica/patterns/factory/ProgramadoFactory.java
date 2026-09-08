package com.logistica.patterns.factory;

/**
 * Creador Concreto para Envíos Programados.
 */
public class ProgramadoFactory extends EnvioFactory {
    @Override
    public ServicioEnvio crearServicio() {
        return new ServicioProgramado();
    }
}
