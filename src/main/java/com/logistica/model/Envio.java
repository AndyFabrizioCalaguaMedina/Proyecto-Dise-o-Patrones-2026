package com.logistica.model;

import java.time.LocalDateTime;

/**
 * Entidad principal de dominio que representa un envÃ­o / paquete.
 */
public class Envio implements Cloneable {
    private int id;
    private String codigoTracking;
    private String tipoServicio; // EXPRESS, ESTANDAR, PROGRAMADO, INTERPROVINCIAL
    private int idRemitente;
    private String nombreDestinatario;
    private String direccionDestino;
    private String telefonoDestinatario;
    private String emailDestinatario;
    private double pesoKg;
    private double costoTotal;
    private EstadoEnvio estadoActual;
    private LocalDateTime fechaRegistro;

    public Envio() {
        this.estadoActual = EstadoEnvio.REGISTRADO;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Envio(String codigoTracking, String tipoServicio, int idRemitente, String nombreDestinatario,
                 String direccionDestino, String telefonoDestinatario, String emailDestinatario,
                 double pesoKg, double costoTotal) {
        this.codigoTracking = codigoTracking;
        this.tipoServicio = tipoServicio;
        this.idRemitente = idRemitente;
        this.nombreDestinatario = nombreDestinatario;
        this.direccionDestino = direccionDestino;
        this.telefonoDestinatario = telefonoDestinatario;
        this.emailDestinatario = emailDestinatario;
        this.pesoKg = pesoKg;
        this.costoTotal = costoTotal;
        this.estadoActual = EstadoEnvio.REGISTRADO;
        this.fechaRegistro = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigoTracking() { return codigoTracking; }
    public void setCodigoTracking(String codigoTracking) { this.codigoTracking = codigoTracking; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public int getIdRemitente() { return idRemitente; }
    public void setIdRemitente(int idRemitente) { this.idRemitente = idRemitente; }

    public String getNombreDestinatario() { return nombreDestinatario; }
    public void setNombreDestinatario(String nombreDestinatario) { this.nombreDestinatario = nombreDestinatario; }

    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }

    public String getTelefonoDestinatario() { return telefonoDestinatario; }
    public void setTelefonoDestinatario(String telefonoDestinatario) { this.telefonoDestinatario = telefonoDestinatario; }

    public String getEmailDestinatario() { return emailDestinatario; }
    public void setEmailDestinatario(String emailDestinatario) { this.emailDestinatario = emailDestinatario; }

    public double getPesoKg() { return pesoKg; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    public EstadoEnvio getEstadoActual() { return estadoActual; }
    public void setEstadoActual(EstadoEnvio estadoActual) { this.estadoActual = estadoActual; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return String.format("Envio[%s | Servicio: %s | Destinatario: %s | Peso: %.2f kg | Costo: S/ %.2f | Estado: %s]",
                codigoTracking, tipoServicio, nombreDestinatario, pesoKg, costoTotal, estadoActual);
    }

    // =====================================================
    // PATRÓN GoF: PROTOTYPE (Creacional)
    // Permite clonar envíos existentes como plantillas
    // para clientes corporativos con envíos recurrentes.
    // Refuerza OCP: se extiende funcionalidad sin modificar lógica existente.
    // =====================================================
    @Override
    public Envio clone() {
        try {
            Envio clon = (Envio) super.clone();
            // Deep copy: nuevo tracking y fecha para el clon
            clon.setCodigoTracking(null); // Se asignará al registrar
            clon.setFechaRegistro(java.time.LocalDateTime.now());
            clon.setEstadoActual(EstadoEnvio.REGISTRADO);
            clon.setId(0); // Nuevo registro en BD
            return clon;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar el envío", e);
        }
    }
}