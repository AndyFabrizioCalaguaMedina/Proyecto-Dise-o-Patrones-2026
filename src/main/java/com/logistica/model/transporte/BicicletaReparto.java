package com.logistica.model.transporte;

/**
 * Bicicleta ecológica para última milla en zonas urbanas.
 * Cumple LSP al extender directamente de Transporte sin heredar métodos de combustible.
 */
public class BicicletaReparto extends Transporte {

    private boolean tieneMochilaTermica;

    public BicicletaReparto(String identificador, double capacidadCargaKg, boolean tieneMochilaTermica) {
        super(identificador, capacidadCargaKg);
        this.tieneMochilaTermica = tieneMochilaTermica;
    }

    @Override
    public void iniciarJornada(String sector) {
        System.out.println("[Bicicleta " + identificador + "] Repartidor en dos ruedas iniciando entregas en: " + sector);
    }

    @Override
    public void trasladarPaquete(String origen, String destino) {
        System.out.println("[Bicicleta " + identificador + "] Desplazándose ágilmente sin congestión ni emisiones de " + origen + " a " + destino);
    }

    public boolean isTieneMochilaTermica() {
        return tieneMochilaTermica;
    }
}
