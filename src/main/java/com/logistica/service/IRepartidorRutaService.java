package com.logistica.service;

/**
 * ISP (Interface Segregation Principle):
 * Contrato de operaciones específicas y exclusivas para Repartidores Móviles en Ruta.
 */
public interface IRepartidorRutaService {
    void iniciarReparto(String codigoTracking, String idVehiculo);
    void registrarEntregaExitosa(String codigoTracking, String firmaDigital);
    void reportarIncidencia(String codigoTracking, String motivoIncidencia);
}
