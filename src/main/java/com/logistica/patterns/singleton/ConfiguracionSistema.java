package com.logistica.patterns.singleton;

/**
 * Patrón GoF: Singleton (Creacional).
 * Mantiene la configuración global de políticas de entrega, tarifas base y parámetros de SLA.
 */
public class ConfiguracionSistema {

    private static ConfiguracionSistema instancia;

    private String nombreEmpresa;
    private String ruc;
    private String version;
    private int maxIntentosEntrega;
    private double factorRecargoExpress;

    private ConfiguracionSistema() {
        this.nombreEmpresa = "ServiLogística Nacional Perú";
        this.ruc = "20609876543";
        this.version = "1.0.0-2026";
        this.maxIntentosEntrega = 3;
        this.factorRecargoExpress = 1.50;
    }

    public static synchronized ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
        return instancia;
    }

    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getRuc() { return ruc; }
    public String getVersion() { return version; }
    public int getMaxIntentosEntrega() { return maxIntentosEntrega; }
    public double getFactorRecargoExpress() { return factorRecargoExpress; }

    @Override
    public String toString() {
        return "ConfiguracionSistema [" + nombreEmpresa + " | RUC: " + ruc + " | v" + version + "]";
    }
}
