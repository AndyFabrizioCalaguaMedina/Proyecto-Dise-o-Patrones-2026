package com.logistica.dto;

/**
 * DTO para la captura y transferencia de datos al registrar un nuevo envío.
 */
public class EnvioRegistroDTO {
    private String tipoServicio;
    private int idRemitente;
    private String nombreDestinatario;
    private String direccionDestino;
    private String telefonoDestinatario;
    private String emailDestinatario;
    private double pesoKg;
    private double distanciaKm;

    public EnvioRegistroDTO() {}

    public EnvioRegistroDTO(String tipoServicio, int idRemitente, String nombreDestinatario,
                            String direccionDestino, String telefonoDestinatario,
                            String emailDestinatario, double pesoKg, double distanciaKm) {
        this.tipoServicio = tipoServicio;
        this.idRemitente = idRemitente;
        this.nombreDestinatario = nombreDestinatario;
        this.direccionDestino = direccionDestino;
        this.telefonoDestinatario = telefonoDestinatario;
        this.emailDestinatario = emailDestinatario;
        this.pesoKg = pesoKg;
        this.distanciaKm = distanciaKm;
    }

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

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
}
