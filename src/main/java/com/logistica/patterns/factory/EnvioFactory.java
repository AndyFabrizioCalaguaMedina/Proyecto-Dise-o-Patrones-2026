package com.logistica.patterns.factory;

/**
 * Patrón GoF: Factory Method - Creador Abstracto.
 * Declara el método de fábrica que las subclases concretas deben implementar.
 */
public abstract class EnvioFactory {

    // Método fábrica (Factory Method)
    public abstract ServicioEnvio crearServicio();

    // Operación de negocio que utiliza el producto creado por el factory method
    public void procesarEnvio(String codigoTracking, String origen, String destino, double pesoKg, double distanciaKm) {
        ServicioEnvio servicio = crearServicio();
        System.out.println("\n--- [Factory Method] " + servicio.getNombreServicio() + " ---");
        System.out.printf("SLA Promesa: %d horas | Tarifa: S/ %.2f%n",
                servicio.getTiempoEntregaHoras(), servicio.calcularTarifa(pesoKg, distanciaKm));
        servicio.despachar(codigoTracking, origen, destino);
    }
}
