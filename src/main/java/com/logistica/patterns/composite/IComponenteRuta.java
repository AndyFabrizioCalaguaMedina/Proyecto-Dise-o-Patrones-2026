package com.logistica.patterns.composite;

/**
 * Patrón GoF: COMPOSITE (Estructural) — Componente.
 *
 * Define la interfaz común para elementos simples (paradas)
 * y compuestos (rutas que agrupan sub-rutas).
 *
 * Justificación: Una ruta logística puede ser una parada simple
 * o una ruta compuesta que agrupa varias paradas y sub-rutas.
 * Composite permite tratar ambos de forma uniforme para calcular
 * distancias totales y listar todos los puntos de entrega.
 *
 * Principio SOLID reforzado: OCP — nuevos tipos de nodos se agregan
 * sin modificar la estructura existente.
 */
public interface IComponenteRuta {
    /**
     * Retorna la distancia total del componente (hoja o compuesto).
     */
    double getDistanciaTotal();

    /**
     * Muestra el detalle del componente con indentación jerárquica.
     * @param nivel Nivel de profundidad para la indentación visual
     */
    void mostrarDetalle(int nivel);
}