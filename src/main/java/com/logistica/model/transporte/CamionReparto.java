package com.logistica.model.transporte;

/**
 * Camión de distribución para carga pesada y rutas consolidadas.
 */
public class CamionReparto extends TransporteMotorizado {

    private String placa;

    public CamionReparto(String identificador, double capacidadCargaKg, double combustibleInicial, String placa) {
        super(identificador, capacidadCargaKg, combustibleInicial);
        this.placa = placa;
    }

    @Override
    public void iniciarJornada(String sector) {
        System.out.println("[Camión " + identificador + " | Placa: " + placa + "] Iniciando ruta en: " + sector);
    }

    @Override
    public void trasladarPaquete(String origen, String destino) {
        this.nivelCombustibleLitros -= 2.0;
        System.out.println("[Camión " + identificador + "] Trasladando carga de " + origen + " hacia " + destino + ". Combustible restante: " + nivelCombustibleLitros + "L");
    }

    public String getPlaca() { return placa; }
}
