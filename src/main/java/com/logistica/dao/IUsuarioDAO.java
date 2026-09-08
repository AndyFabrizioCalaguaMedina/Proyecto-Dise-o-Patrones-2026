package com.logistica.dao;

import com.logistica.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * DIP & DAO Pattern:
 * Contrato de acceso a datos para la entidad Usuario.
 */
public interface IUsuarioDAO {
    boolean insertar(Usuario usuario);
    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorDocumento(String documento);
    List<Usuario> listarTodos();
}
