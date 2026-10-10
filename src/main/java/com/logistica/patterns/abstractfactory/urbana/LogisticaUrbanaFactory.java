package com.logistica.patterns.abstractfactory.urbana;

import com.logistica.patterns.abstractfactory.*;

/**
 * Fábrica Concreta (Familia Urbana):
 * Crea componentes logísticos coherentes para operación dentro de la ciudad.
 */
public class LogisticaUrbanaFactory implements ILogisticaFactory {
    @Override
    public ITransporteLogistico crearTransporte() { return new MotoReparto(); }

    @Override
    public IEmpaque crearEmpaque() { return new EmpaqueEstandar(); }

    @Override
    public IRutaLogistica crearRuta() { return new RutaLocal(); }
}