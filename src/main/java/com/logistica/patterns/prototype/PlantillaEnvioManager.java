package com.logistica.patterns.prototype;

import com.logistica.model.Envio;
import java.util.HashMap;
import java.util.Map;

/**
 * Patrón GoF: PROTOTYPE (Creacional) — Gestor de Plantillas.
 *
 * Almacena prototipos de envíos frecuentes que los clientes corporativos
 * pueden clonar para agilizar registros repetitivos al mismo destinatario/ruta.
 *
 * Principio SOLID reforzado: OCP — se agregan nuevas plantillas sin modificar
 * la lógica de registro existente.
 */
public class PlantillaEnvioManager {

    private final Map<String, Envio> plantillas = new HashMap<>();

    /**
     * Registra un envío existente como plantilla reutilizable.
     * @param nombrePlantilla Identificador de la plantilla (ej. "envio-diario-lima-norte")
     * @param envioOriginal   Envío base que servirá como prototipo
     */
    public void registrarPlantilla(String nombrePlantilla, Envio envioOriginal) {
        plantillas.put(nombrePlantilla, envioOriginal);
        System.out.println("[PROTOTYPE] Plantilla '" + nombrePlantilla + "' registrada con éxito.");
    }

    /**
     * Clona una plantilla previamente registrada.
     * Devuelve una copia independiente con tracking y fecha reiniciados.
     * @param nombrePlantilla Nombre de la plantilla a clonar
     * @return Nuevo Envio clonado, listo para personalizar y registrar
     */
    public Envio clonarPlantilla(String nombrePlantilla) {
        Envio prototipo = plantillas.get(nombrePlantilla);
        if (prototipo == null) {
            throw new IllegalArgumentException("Plantilla '" + nombrePlantilla + "' no encontrada.");
        }
        Envio clon = prototipo.clone();
        System.out.println("[PROTOTYPE] Envío clonado desde plantilla '" + nombrePlantilla + "'");
        return clon;
    }

    /**
     * Lista todas las plantillas disponibles.
     */
    public void listarPlantillas() {
        System.out.println("\n=== PLANTILLAS DE ENVÍO DISPONIBLES ===");
        if (plantillas.isEmpty()) {
            System.out.println("  (Sin plantillas registradas)");
            return;
        }
        plantillas.forEach((nombre, envio) ->
            System.out.printf("  [%s] → Destino: %s | Servicio: %s | Peso: %.2f kg%n",
                nombre, envio.getDireccionDestino(), envio.getTipoServicio(), envio.getPesoKg())
        );
    }

    public Map<String, Envio> getPlantillas() {
        return plantillas;
    }
}