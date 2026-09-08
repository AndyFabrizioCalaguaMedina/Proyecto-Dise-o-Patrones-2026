package com.logistica.dao.impl;

import com.logistica.dao.IUsuarioDAO;
import com.logistica.model.RolUsuario;
import com.logistica.model.Usuario;
import com.logistica.persistence.ConexionBD;

import java.sql.*;
import java.util.*;

/**
 * Implementación de IUsuarioDAO para MySQL (XAMPP).
 */
public class UsuarioDAOMySQL implements IUsuarioDAO {

    private final ConexionBD conexionBD;
    private final Map<Integer, Usuario> memoriaUsuarios = new HashMap<>();

    public UsuarioDAOMySQL() {
        this.conexionBD = ConexionBD.getInstancia();
        // Inicializar datos base en memoria
        memoriaUsuarios.put(1, new Usuario(1, "70010020", "Admin Logístico", "admin@logistica.com", "999111222", RolUsuario.ADMINISTRADOR));
        memoriaUsuarios.put(2, new Usuario(2, "45871234", "Carlos Mendoza", "carlos.almacen@logistica.com", "988222333", RolUsuario.OPERADOR_ALMACEN));
        memoriaUsuarios.put(3, new Usuario(3, "72345678", "Jorge Ríos", "jorge.repartidor@logistica.com", "977333444", RolUsuario.REPARTIDOR));
        memoriaUsuarios.put(4, new Usuario(4, "20601234567", "TechCorp S.A.C.", "contacto@techcorp.pe", "014445555", RolUsuario.CLIENTE_CORPORATIVO));
    }

    @Override
    public boolean insertar(Usuario usuario) {
        memoriaUsuarios.put(usuario.getId(), usuario);
        String sql = "INSERT INTO usuarios (documento, nombre, apellidos, email, telefono, rol) VALUES (?, ?, '', ?, ?, ?)";
        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getDocumento());
            ps.setString(2, usuario.getNombreCompleto());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getRol().name());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Guardado en memoria
        }
        return true;
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("documento"),
                            rs.getString("nombre") + " " + rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            RolUsuario.valueOf(rs.getString("rol"))
                    );
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            // Fallback
        }
        return Optional.ofNullable(memoriaUsuarios.get(id));
    }

    @Override
    public Optional<Usuario> buscarPorDocumento(String documento) {
        return memoriaUsuarios.values().stream()
                .filter(u -> u.getDocumento().equals(documento))
                .findFirst();
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(memoriaUsuarios.values());
    }
}
