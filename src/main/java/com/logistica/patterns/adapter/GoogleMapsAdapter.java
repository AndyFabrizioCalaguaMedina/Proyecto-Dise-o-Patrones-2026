package com.logistica.patterns.adapter;

/**
 * Patrón GoF: ADAPTER (Estructural) — Adaptador Concreto.
 *
 * Justificación: El sistema logístico necesita obtener ubicaciones GPS
 * de proveedores externos (Google Maps, Waze, API local). Cada proveedor
 * tiene una API con formatos distintos. El Adapter convierte la interfaz
 * externa incompatible en la interfaz interna IGeolocalizador.
 *
 * Principios SOLID reforzados:
 * - OCP: para agregar un nuevo proveedor (ej. WazeAdapter) solo se crea
 *   otra clase Adapter sin modificar código existente.
 * - DIP: el sistema depende de IGeolocalizador, no de GoogleMapsAPI.
 * - SRP: esta clase tiene la única responsabilidad de traducir formatos.
 */
public class GoogleMapsAdapter implements IGeolocalizador {

    private final GoogleMapsAPI googleMaps;

    public GoogleMapsAdapter() {
        this.googleMaps = new GoogleMapsAPI();
    }

    @Override
    public double[] obtenerCoordenadas(String direccion) {
        // Adaptación: convierte "lat,lng" (String) → double[] {lat, lng}
        String resultado = googleMaps.getLatLng(direccion);
        String[] partes = resultado.split(",");
        double lat = Double.parseDouble(partes[0]);
        double lng = Double.parseDouble(partes[1]);
        System.out.printf("  [ADAPTER] Coordenadas adaptadas: [%.6f, %.6f]%n", lat, lng);
        return new double[]{lat, lng};
    }

    @Override
    public double calcularDistancia(String origen, String destino) {
        // Adaptación: convierte metros (int) → kilómetros (double)
        int metros = googleMaps.getDistanceInMeters(origen, destino);
        double km = metros / 1000.0;
        System.out.printf("  [ADAPTER] Distancia adaptada: %d metros → %.2f km%n", metros, km);
        return km;
    }
}