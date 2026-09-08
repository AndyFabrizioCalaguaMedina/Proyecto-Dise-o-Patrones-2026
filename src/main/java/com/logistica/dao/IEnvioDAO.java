package com.logistica.dao;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import java.util.List;
import java.util.Optional;

/**
 * DIP (Dependency Inversion Principle) & DAO Pattern:
 * Contrato de acceso a datos para la entidad Envio.
 */
public interface IEnvioDAO {
    boolean insertar(Envio envio);
    Optional<Envio> buscarPorTracking(String codigoTracking);
    List<Envio> listarTodos();
    boolean actualizarEstado(String codigoTracking, EstadoEnvio nuevoEstado, String ubicacion, String observacion);
}
