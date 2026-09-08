package com.logistica.util;

import java.util.Random;

/**
 * Utilidad para generación de códigos únicos de tracking y guías.
 */
public class GeneradorCodigo {

    private static final Random RANDOM = new Random();

    public static String generarTracking(String tipoServicio) {
        String prefijo;
        switch (tipoServicio.toUpperCase()) {
            case "EXPRESS":
                prefijo = "EXP";
                break;
            case "PROGRAMADO":
                prefijo = "PRG";
                break;
            case "INTERPROVINCIAL":
                prefijo = "INT";
                break;
            case "ESTANDAR":
            default:
                prefijo = "STD";
                break;
        }
        int numero = 100000 + RANDOM.nextInt(900000);
        return "TRK-" + prefijo + "-" + numero;
    }
}
