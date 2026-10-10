package com.logistica.patterns.abstractfactory.interprovincial;

import com.logistica.patterns.abstractfactory.*;

/**
 * Fábrica Concreta (Familia Interprovincial):
 * Crea componentes logísticos coherentes para operación de larga distancia.
 */
public class LogisticaInterprovincialFactory implements ILogisticaFactory {
    @Override
    public ITransporteLogistico crearTransporte() { return new CamionCarga(); }

    @Override
    public IEmpaque crearEmpaque() { return new EmpaqueBlindado(); }

    @Override
    public IRutaLogistica crearRuta() { return new RutaNacional(); }
}