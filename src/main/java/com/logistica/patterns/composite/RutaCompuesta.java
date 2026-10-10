package com.logistica.patterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón GoF: COMPOSITE — Compuesto (Composite).
 *
 * Representa una ruta que agrupa múltiples paradas (hojas)
 * y/o sub-rutas (otros composites), formando una estructura
 * jerárquica de tipo árbol.
 *
 * Ejemplo: Ruta "Lima Norte" contiene 5 paradas individuales.
 * Ruta "Lima Metropolitana" agrupa "Lima Norte", "Lima Sur" y "Lima Centro".
 */
public class RutaCompuesta implements IComponenteRuta {

    private final String nombreRuta;
    private final List<IComponenteRuta> componentes = new ArrayList<>();

    public RutaCompuesta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    /**
     * Agrega un componente (parada o sub-ruta) a esta ruta.
     */
    public void agregar(IComponenteRuta componente) {
        componentes.add(componente);
    }

    /**
     * Remueve un componente de esta ruta.
     */
    public void remover(IComponenteRuta componente) {
        componentes.remove(componente);
    }

    @Override
    public double getDistanciaTotal() {
        // Suma recursiva de distancias de todos los hijos
        return componentes.stream()
                .mapToDouble(IComponenteRuta::getDistanciaTotal)
                .sum();
    }

    @Override
    public void mostrarDetalle(int nivel) {
        String indent = "  ".repeat(nivel);
        System.out.printf("%s📦 Ruta [%s] — Distancia total: %.1f km — %d componente(s)%n",
                indent, nombreRuta, getDistanciaTotal(), componentes.size());
        for (IComponenteRuta componente : componentes) {
            componente.mostrarDetalle(nivel + 1);
        }
    }

    public String getNombreRuta() { return nombreRuta; }
    public List<IComponenteRuta> getComponentes() { return componentes; }
}