package com.logistica.patterns.adapter;

/**
 * Patrón GoF: ADAPTER — Adaptee (Clase existente con interfaz incompatible).
 *
 * Simula la API de Google Maps con métodos y formatos DISTINTOS a los
 * que nuestro sistema logístico espera. En un escenario real, esta clase
 * sería un SDK de terceros que NO podemos modificar.
 */
public class GoogleMapsAPI {

    /**
     * Método de Google Maps: retorna coordenadas como String "lat,lng".
     * NOTA: Formato incompatible con nuestro IGeolocalizador (que espera double[]).
     */
    public String getLatLng(String address) {
        System.out.println("  [Google Maps API] Consultando coordenadas para: " + address);
        // Simulación de respuesta de API
        double lat = -12.0464 + (Math.random() * 0.1);
        double lng = -77.0428 + (Math.random() * 0.1);
        return String.format("%.6f,%.6f", lat, lng);
    }

    /**
     * Método de Google Maps: calcula distancia en METROS (no kilómetros).
     */
    public int getDistanceInMeters(String originAddress, String destinationAddress) {
        System.out.println("  [Google Maps API] Calculando distancia en metros...");
        // Simulación
        return 5000 + (int)(Math.random() * 20000);
    }
}