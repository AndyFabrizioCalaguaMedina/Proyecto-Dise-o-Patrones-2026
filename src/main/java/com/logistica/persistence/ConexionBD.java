package com.logistica.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Patrón Singleton en la capa de persistencia:
 * Administra y provee de forma centralizada la conexión a la base de datos MySQL (XAMPP).
 */
public class ConexionBD {

    private static ConexionBD instancia;

    private final String url;
    private final String usuario;
    private final String clave;

    private ConexionBD() {
        // Parámetros por defecto en XAMPP
        String host = "localhost";
        int puerto = 3306;
        String baseDatos = "db_logistica_envios";
        this.usuario = "root";
        this.clave = ""; // Contraseña vacía por defecto en XAMPP
        this.url = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // El driver se carga dinámicamente si está en el classpath
        }
        return DriverManager.getConnection(url, usuario, clave);
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }
}
