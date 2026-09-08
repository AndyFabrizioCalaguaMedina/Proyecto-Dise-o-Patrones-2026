package com.logistica.service;

import com.logistica.model.Envio;

/**
 * ISP (Interface Segregation Principle):
 * Contrato de operaciones específicas y exclusivas para Operadores de Almacén.
 */
public interface IOperadorAlmacenService {
    void recepcionarPaquete(Envio envio);
    void clasificarPaquete(Envio envio, String sectorBahia);
    void consolidarLote(String idContenedor);
}
