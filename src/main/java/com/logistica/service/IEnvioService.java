package com.logistica.service;

import com.logistica.dto.EnvioRegistroDTO;
import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de servicio para operaciones de envíos (DIP / ISP).
 */
public interface IEnvioService {
    RespuestaOperacionDTO registrarEnvio(EnvioRegistroDTO dto);
    Optional<Envio> consultarTracking(String codigoTracking);
    List<Envio> listarTodos();
    RespuestaOperacionDTO actualizarEstadoEnvio(String codigoTracking, EstadoEnvio nuevoEstado, String ubicacion, String observacion);
}
