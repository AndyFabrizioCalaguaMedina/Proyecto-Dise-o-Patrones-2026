package com.logistica.model;

/**
 * Entidad de dominio que representa a un usuario del sistema logístico.
 */
public class Usuario {
    private int id;
    private String documento;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private RolUsuario rol;

    public Usuario() {}

    public Usuario(int id, String documento, String nombreCompleto, String email, String telefono, RolUsuario rol) {
        this.id = id;
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Usuario[ID=" + id + ", Doc=" + documento + ", Nombre=" + nombreCompleto + ", Rol=" + rol + "]";
    }
}
