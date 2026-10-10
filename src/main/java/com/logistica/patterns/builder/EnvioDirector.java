package com.logistica.patterns.builder;

import com.logistica.model.Envio;

/**
 * Patrón GoF: BUILDER — Director.
 *
 * Define recetas predefinidas de construcción para tipos comunes de envíos,
 * encapsulando la secuencia de pasos del Builder.
 *
 * El Director NO conoce los detalles internos del producto; solo orquesta
 * la secuencia de invocaciones al Builder.
 */
public class EnvioDirector {

    /**
     * Construye un envío Express con seguro y prioridad urgente.
     */
    public Envio crearEnvioExpress(int idRemitente, String destinatario,
                                    String direccion, String email, double pesoKg) {
        System.out.println("\n[DIRECTOR] Creando envío EXPRESS predefinido...");
        return new EnvioBuilder()
                .conTipoServicio("EXPRESS")
                .conRemitente(idRemitente)
                .conDestinatario(destinatario, direccion)
                .conContacto("", email)
                .conPeso(pesoKg)
                .conCosto(25.00 + pesoKg * 5.00)
                .conSeguro(50.00)
                .conPrioridad(1)
                .build();
    }

    /**
     * Construye un envío corporativo completo con todos los extras.
     */
    public Envio crearEnvioCorporativo(int idRemitente, String destinatario,
                                        String direccion, String telefono, String email,
                                        double pesoKg, String instrucciones) {
        System.out.println("\n[DIRECTOR] Creando envío CORPORATIVO predefinido...");
        return new EnvioBuilder()
                .conTipoServicio("EXPRESS")
                .conRemitente(idRemitente)
                .conDestinatario(destinatario, direccion)
                .conContacto(telefono, email)
                .conPeso(pesoKg)
                .conCosto(35.00 + pesoKg * 6.00)
                .conSeguro(200.00)
                .conEmpaqueReforzado()
                .conRetorno()
                .conInstrucciones(instrucciones)
                .conHorarioPreferido("09:00 - 12:00")
                .conPrioridad(1)
                .build();
    }

    /**
     * Construye un envío económico estándar sin extras.
     */
    public Envio crearEnvioEconomico(int idRemitente, String destinatario,
                                      String direccion, double pesoKg) {
        System.out.println("\n[DIRECTOR] Creando envío ECONÓMICO predefinido...");
        return new EnvioBuilder()
                .conTipoServicio("ESTANDAR")
                .conRemitente(idRemitente)
                .conDestinatario(destinatario, direccion)
                .conPeso(pesoKg)
                .conCosto(12.00 + pesoKg * 3.00)
                .conPrioridad(3)
                .build();
    }
}