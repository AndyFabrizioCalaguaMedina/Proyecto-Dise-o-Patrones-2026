package com.logistica.service.impl;

import com.logistica.service.IRepartidorRutaService;

/**
 * ISP: Implementación de servicios para Repartidores Móviles en Ruta.
 */
public class RepartidorRutaServiceImpl implements IRepartidorRutaService {

    private final String nombreRepartidor;

    public RepartidorRutaServiceImpl(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    @Override
    public void iniciarReparto(String codigoTracking, String idVehiculo) {
        System.out.println("[App Móvil Repartidor] " + nombreRepartidor + " inició ruta para guía " + codigoTracking + " en vehículo " + idVehiculo);
    }

    @Override
    public void registrarEntregaExitosa(String codigoTracking, String firmaDigital) {
        System.out.println("[App Móvil Repartidor] Entrega de " + codigoTracking + " confirmada con firma digital hash (" + firmaDigital.hashCode() + ")");
    }

    @Override
    public void reportarIncidencia(String codigoTracking, String motivoIncidencia) {
        System.out.println("[App Móvil Repartidor] Incidencia registrada en guía " + codigoTracking + ": " + motivoIncidencia);
    }
}
