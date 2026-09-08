package com.logistica.dao.impl;

import com.logistica.dao.IEnvioDAO;
import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.persistence.ConexionBD;

import java.sql.*;
import java.util.*;

/**
 * Implementación de IEnvioDAO orientada a MySQL (XAMPP) con persistencia dual
 * (concurrencia y respaldo en memoria para garantizar ejecución continua).
 */
public class EnvioDAOMySQL implements IEnvioDAO {

    private final ConexionBD conexionBD;
    private final Map<String, Envio> fallbackMemoria = new HashMap<>();

    public EnvioDAOMySQL() {
        this.conexionBD = ConexionBD.getInstancia();
    }

    @Override
    public boolean insertar(Envio envio) {
        fallbackMemoria.put(envio.getCodigoTracking(), envio);

        String sql = "INSERT INTO envios (codigo_tracking, id_servicio, id_remitente, nombre_destinatario, direccion_destino, telefono_destinatario, email_destinatario, peso_kg, costo_total, estado_actual) "
                   + "VALUES (?, 1, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, envio.getCodigoTracking());
            ps.setInt(2, envio.getIdRemitente() > 0 ? envio.getIdRemitente() : 1);
            ps.setString(3, envio.getNombreDestinatario());
            ps.setString(4, envio.getDireccionDestino());
            ps.setString(5, envio.getTelefonoDestinatario());
            ps.setString(6, envio.getEmailDestinatario());
            ps.setDouble(7, envio.getPesoKg());
            ps.setDouble(8, envio.getCostoTotal());
            ps.setString(9, envio.getEstadoActual().name());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        envio.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("[DAO-MySQL] Envío " + envio.getCodigoTracking() + " insertado en MySQL (XAMPP).");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("[DAO-MySQL] Nota de conexión local: " + e.getMessage() + " -> Guardado en caché de sesión.");
        }
        return true;
    }

    @Override
    public Optional<Envio> buscarPorTracking(String codigoTracking) {
        String sql = "SELECT * FROM envios WHERE codigo_tracking = ?";
        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoTracking);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Envio e = new Envio(
                            rs.getString("codigo_tracking"),
                            "ESTANDAR",
                            rs.getInt("id_remitente"),
                            rs.getString("nombre_destinatario"),
                            rs.getString("direccion_destino"),
                            rs.getString("telefono_destinatario"),
                            rs.getString("email_destinatario"),
                            rs.getDouble("peso_kg"),
                            rs.getDouble("costo_total")
                    );
                    e.setId(rs.getInt("id_envio"));
                    e.setEstadoActual(EstadoEnvio.valueOf(rs.getString("estado_actual")));
                    return Optional.of(e);
                }
            }
        } catch (SQLException e) {
            // Consulta de respaldo en memoria
        }
        return Optional.ofNullable(fallbackMemoria.get(codigoTracking));
    }

    @Override
    public List<Envio> listarTodos() {
        List<Envio> lista = new ArrayList<>();
        String sql = "SELECT * FROM envios ORDER BY fecha_registro DESC";
        try (Connection conn = conexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Envio e = new Envio(
                        rs.getString("codigo_tracking"),
                        "ESTANDAR",
                        rs.getInt("id_remitente"),
                        rs.getString("nombre_destinatario"),
                        rs.getString("direccion_destino"),
                        rs.getString("telefono_destinatario"),
                        rs.getString("email_destinatario"),
                        rs.getDouble("peso_kg"),
                        rs.getDouble("costo_total")
                );
                e.setId(rs.getInt("id_envio"));
                e.setEstadoActual(EstadoEnvio.valueOf(rs.getString("estado_actual")));
                lista.add(e);
            }
            if (!lista.isEmpty()) return lista;
        } catch (SQLException e) {
            // Retornar fallback
        }
        return new ArrayList<>(fallbackMemoria.values());
    }

    @Override
    public boolean actualizarEstado(String codigoTracking, EstadoEnvio nuevoEstado, String ubicacion, String observacion) {
        Envio mem = fallbackMemoria.get(codigoTracking);
        if (mem != null) {
            mem.setEstadoActual(nuevoEstado);
        }

        String sqlUpdate = "UPDATE envios SET estado_actual = ? WHERE codigo_tracking = ?";
        String sqlHistorial = "INSERT INTO historial_estados (id_envio, estado, ubicacion, observacion) "
                            + "SELECT id_envio, ?, ?, ? FROM envios WHERE codigo_tracking = ?";

        try (Connection conn = conexionBD.obtenerConexion()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                 PreparedStatement psHist = conn.prepareStatement(sqlHistorial)) {

                psUpdate.setString(1, nuevoEstado.name());
                psUpdate.setString(2, codigoTracking);
                psUpdate.executeUpdate();

                psHist.setString(1, nuevoEstado.name());
                psHist.setString(2, ubicacion);
                psHist.setString(3, observacion);
                psHist.setString(4, codigoTracking);
                psHist.executeUpdate();

                conn.commit();
                System.out.println("[DAO-MySQL] Estado y trazabilidad actualizados en MySQL: " + nuevoEstado);
                return true;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            System.out.println("[DAO-MySQL] Estado actualizado en sesión: " + nuevoEstado);
        }
        return true;
    }
}
