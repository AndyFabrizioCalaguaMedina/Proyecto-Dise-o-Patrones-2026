package com.logistica.patterns.builder;

import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.util.GeneradorCodigo;

/**
 * Patrón GoF: BUILDER (Creacional) — Constructor paso a paso de envíos complejos.
 *
 * Justificación: Un envío corporativo o de carga especial puede tener más de 10
 * campos opcionales (seguro, empaque reforzado, retorno, prioridad, instrucciones
 * especiales, horario preferido). Builder permite construir objetos complejos
 * de forma legible evitando constructores telescópicos.
 *
 * Principio SOLID reforzado: SRP — la lógica de construcción se separa de la entidad.
 */
public class EnvioBuilder {

    private String tipoServicio = "ESTANDAR";
    private int idRemitente;
    private String nombreDestinatario;
    private String direccionDestino;
    private String telefonoDestinatario = "";
    private String emailDestinatario = "";
    private double pesoKg;
    private double costoTotal;

    // Campos opcionales avanzados
    private boolean seguroContratado = false;
    private double montoSeguro = 0.0;
    private boolean empaqueReforzado = false;
    private boolean requiereRetorno = false;
    private String instruccionesEspeciales = "";
    private String horarioPreferido = "";
    private int prioridadEntrega = 3; // 1=Urgente, 2=Alta, 3=Normal

    public EnvioBuilder() {}

    // --- Métodos obligatorios ---
    public EnvioBuilder conTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
        return this;
    }

    public EnvioBuilder conRemitente(int idRemitente) {
        this.idRemitente = idRemitente;
        return this;
    }

    public EnvioBuilder conDestinatario(String nombre, String direccion) {
        this.nombreDestinatario = nombre;
        this.direccionDestino = direccion;
        return this;
    }

    public EnvioBuilder conContacto(String telefono, String email) {
        this.telefonoDestinatario = telefono;
        this.emailDestinatario = email;
        return this;
    }

    public EnvioBuilder conPeso(double pesoKg) {
        this.pesoKg = pesoKg;
        return this;
    }

    public EnvioBuilder conCosto(double costoTotal) {
        this.costoTotal = costoTotal;
        return this;
    }

    // --- Métodos opcionales (fluent API) ---
    public EnvioBuilder conSeguro(double montoSeguro) {
        this.seguroContratado = true;
        this.montoSeguro = montoSeguro;
        return this;
    }

    public EnvioBuilder conEmpaqueReforzado() {
        this.empaqueReforzado = true;
        return this;
    }

    public EnvioBuilder conRetorno() {
        this.requiereRetorno = true;
        return this;
    }

    public EnvioBuilder conInstrucciones(String instrucciones) {
        this.instruccionesEspeciales = instrucciones;
        return this;
    }

    public EnvioBuilder conHorarioPreferido(String horario) {
        this.horarioPreferido = horario;
        return this;
    }

    public EnvioBuilder conPrioridad(int prioridad) {
        this.prioridadEntrega = prioridad;
        return this;
    }

    /**
     * Construye el objeto Envio final con todos los parámetros configurados.
     * @return Envio construido y listo para persistir
     */
    public Envio build() {
        String tracking = GeneradorCodigo.generarTracking(tipoServicio);

        Envio envio = new Envio(
            tracking, tipoServicio, idRemitente, nombreDestinatario,
            direccionDestino, telefonoDestinatario, emailDestinatario,
            pesoKg, costoTotal
        );

        // Imprimir resumen de construcción
        System.out.println("\n[BUILDER] Envío construido paso a paso:");
        System.out.printf("  Tracking: %s | Servicio: %s | Peso: %.2f kg%n", tracking, tipoServicio, pesoKg);
        System.out.printf("  Destinatario: %s → %s%n", nombreDestinatario, direccionDestino);
        if (seguroContratado) System.out.printf("  + Seguro contratado: S/ %.2f%n", montoSeguro);
        if (empaqueReforzado) System.out.println("  + Empaque reforzado activado");
        if (requiereRetorno)  System.out.println("  + Retorno al remitente activado");
        if (!instruccionesEspeciales.isEmpty()) System.out.println("  + Instrucciones: " + instruccionesEspeciales);
        if (!horarioPreferido.isEmpty()) System.out.println("  + Horario preferido: " + horarioPreferido);
        System.out.println("  Prioridad: " + (prioridadEntrega == 1 ? "URGENTE" : prioridadEntrega == 2 ? "ALTA" : "NORMAL"));

        return envio;
    }
}