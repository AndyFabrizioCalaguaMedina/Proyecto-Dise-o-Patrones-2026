package com.logistica.controller;

import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.EstadoEnvio;
import com.logistica.service.IEnvioService;

/**
 * Controlador para la actualización y consulta de estados de trazabilidad.
 */
public class TrackingController {

    private final IEnvioService envioService;

    public TrackingController(IEnvioService envioService) {
        this.envioService = envioService;
    }

    public RespuestaOperacionDTO actualizarEstado(String tracking, EstadoEnvio nuevoEstado, String ubicacion, String observacion) {
        return envioService.actualizarEstadoEnvio(tracking, nuevoEstado, ubicacion, observacion);
    }
}
