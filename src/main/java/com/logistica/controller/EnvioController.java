package com.logistica.controller;

import com.logistica.dto.EnvioRegistroDTO;
import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.Envio;
import com.logistica.service.IEnvioService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión de envíos (Capa de Controladores).
 */
public class EnvioController {

    private final IEnvioService envioService;

    public EnvioController(IEnvioService envioService) {
        this.envioService = envioService;
    }

    public RespuestaOperacionDTO registrarEnvio(EnvioRegistroDTO dto) {
        return envioService.registrarEnvio(dto);
    }

    public List<Envio> listarEnvios() {
        return envioService.listarTodos();
    }

    public Optional<Envio> buscarEnvio(String tracking) {
        return envioService.consultarTracking(tracking);
    }
}
