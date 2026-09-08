package com.logistica.ui;

import com.logistica.controller.EnvioController;
import com.logistica.controller.TrackingController;
import com.logistica.dto.EnvioRegistroDTO;
import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.model.transporte.*;
import com.logistica.patterns.factory.*;
import com.logistica.patterns.singleton.ConfiguracionSistema;
import com.logistica.persistence.ConexionBD;

import java.util.List;

/**
 * Capa de Interfaz de Usuario (UI) por Consola.
 * Proporciona vistas y demostraciones claras del sistema.
 */
public class ConsolaView {

    private final EnvioController envioController;
    private final TrackingController trackingController;

    public ConsolaView(EnvioController envioController, TrackingController trackingController) {
        this.envioController = envioController;
        this.trackingController = trackingController;
    }

    public void renderizarDemostracionCompleta() {
        imprimirBanner("PLATAFORMA DE LOGÍSTICA Y SEGUIMIENTO DE ENVÍOS");
        System.out.println("UNIVERSIDAD TECNOLÓGICA DEL PERÚ - DISEÑO DE PATRONES");
        System.out.println("Demostración Arquitectónica del Primer Avance (Semanas 1 a 4)\n");

        // 1. Singleton (Configuración y Base de Datos)
        imprimirSubseccion("1. PATRÓN SINGLETON (Configuración del Sistema y Conexión XAMPP)");
        ConfiguracionSistema config = ConfiguracionSistema.getInstancia();
        System.out.println("Configuración Global: " + config);
        ConexionBD connBD = ConexionBD.getInstancia();
        System.out.println("Instancia Conexión MySQL: " + connBD.getUrl() + " (Usuario: " + connBD.getUsuario() + ")");

        // 2. Factory Method (Creación desacoplada de envíos)
        imprimirSubseccion("2. PATRÓN FACTORY METHOD (Creación de Servicios Logísticos)");
        EnvioFactory factoryExpress = new ExpressFactory();
        factoryExpress.procesarEnvio("TRK-EXP-1010", "Centro Logístico Lima", "Miraflores", 2.5, 8.0);

        EnvioFactory factoryEstandar = new EstandarFactory();
        factoryEstandar.procesarEnvio("TRK-STD-2020", "Hub Principal Lima", "Trujillo", 6.0, 560.0);

        EnvioFactory factoryProgramado = new ProgramadoFactory();
        factoryProgramado.procesarEnvio("TRK-PRG-3030", "Almacén San Isidro", "Surco", 1.2, 12.0);

        // 3. LSP (Principio de Sustitución de Liskov en Transportes)
        imprimirSubseccion("3. PRINCIPIO LSP (Polimorfismo en Jerarquía de Transporte)");
        Transporte camion = new CamionReparto("CAM-01", 2000.0, 50.0, "ABC-123");
        Transporte bici = new BicicletaReparto("BIC-01", 20.0, true);

        ejecutarRuta(camion, "Almacén Central", "San Juan de Lurigancho");
        ejecutarRuta(bici, "Punto Express Miraflores", "San Isidro");

        // 4. Registro y Flujo de Negocio (SRP, OCP, DIP)
        imprimirSubseccion("4. REGISTRO DE ENVÍO Y PERSISTENCIA (SRP, OCP, DIP)");
        EnvioRegistroDTO nuevoEnvio = new EnvioRegistroDTO(
                "EXPRESS",
                4, // TechCorp
                "Valeria Ramos",
                "Av. Javier Prado Este 2500, San Borja",
                "966444555",
                "valeria.ramos@gmail.com",
                3.2,
                15.0
        );

        RespuestaOperacionDTO res = envioController.registrarEnvio(nuevoEnvio);
        Envio envioCreado = (Envio) res.getData();
        System.out.println("Resultado: " + res.getMensaje());
        System.out.println("Detalle: " + envioCreado);

        // 5. Patrón Observer (Trazabilidad y Notificaciones Reactivas)
        imprimirSubseccion("5. PATRÓN OBSERVER (Trazabilidad en Tiempo Real y Notificaciones Reactivas)");
        String tracking = envioCreado.getCodigoTracking();

        trackingController.actualizarEstado(tracking, EstadoEnvio.RECOGIDO, "Tienda Origen", "Paquete recogido por el transportista");
        trackingController.actualizarEstado(tracking, EstadoEnvio.CLASIFICADO, "Hub Lima Este", "Clasificado en faja automática");
        trackingController.actualizarEstado(tracking, EstadoEnvio.EN_RUTA, "San Borja", "En unidad de reparto de última milla");
        trackingController.actualizarEstado(tracking, EstadoEnvio.OBSERVADO, "San Borja", "Destinatario no responde al timbre");
        trackingController.actualizarEstado(tracking, EstadoEnvio.ENTREGADO, "San Borja", "Entregado a recepcionista autorizado");

        // 6. Listado General
        imprimirSubseccion("6. CONSULTA GENERAL DE ENVÍOS EN BASE DE DATOS");
        List<Envio> listado = envioController.listarEnvios();
        for (Envio e : listado) {
            System.out.println(" -> " + e);
        }

        imprimirBanner("DEMOSTRACIÓN FINALIZADA CON ÉXITO");
    }

    private void ejecutarRuta(Transporte transporte, String origen, String destino) {
        transporte.iniciarJornada("Zona Urbana");
        transporte.trasladarPaquete(origen, destino);
    }

    private void imprimirBanner(String texto) {
        System.out.println("================================================================================");
        System.out.println("   " + texto);
        System.out.println("================================================================================");
    }

    private void imprimirSubseccion(String texto) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> " + texto);
        System.out.println("--------------------------------------------------------------------------------");
    }
}
