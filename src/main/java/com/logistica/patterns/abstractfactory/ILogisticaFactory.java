package com.logistica.patterns.abstractfactory;

/**
 * Patrón GoF: ABSTRACT FACTORY (Creacional) — Fábrica Abstracta.
 *
 * Justificación: La operación logística varía según la zona geográfica.
 * Zona URBANA requiere motos, empaque estándar y rutas locales.
 * Zona INTERPROVINCIAL requiere camiones, empaque blindado y rutas nacionales.
 *
 * Abstract Factory garantiza que los componentes creados sean coherentes
 * dentro de cada familia/zona, evitando mezclas incompatibles.
 *
 * Principio SOLID reforzado: OCP — agregar una nueva zona (ej. INTERNACIONAL)
 * solo requiere una nueva fábrica concreta sin modificar el código existente.
 * DIP — el cliente depende de la interfaz ILogisticaFactory, no de clases concretas.
 */
public interface ILogisticaFactory {
    ITransporteLogistico crearTransporte();
    IEmpaque crearEmpaque();
    IRutaLogistica crearRuta();
}