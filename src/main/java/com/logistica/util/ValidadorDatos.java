package com.logistica.util;

/**
 * Utilidad para validación de datos de entrada.
 */
public class ValidadorDatos {

    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean esNumeroPositivo(double valor) {
        return valor > 0;
    }
}
