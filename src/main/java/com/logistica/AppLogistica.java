package com.logistica;

import com.logistica.controller.EnvioController;
import com.logistica.controller.TrackingController;
import com.logistica.dao.IEnvioDAO;
import com.logistica.dao.impl.EnvioDAOMySQL;
import com.logistica.model.Envio;
import com.logistica.patterns.abstractfactory.IEmpaque;
import com.logistica.patterns.abstractfactory.ILogisticaFactory;
import com.logistica.patterns.abstractfactory.IRutaLogistica;
import com.logistica.patterns.abstractfactory.ITransporteLogistico;
import com.logistica.patterns.abstractfactory.interprovincial.LogisticaInterprovincialFactory;
import com.logistica.patterns.abstractfactory.urbana.LogisticaUrbanaFactory;
import com.logistica.patterns.adapter.GoogleMapsAdapter;
import com.logistica.patterns.adapter.IGeolocalizador;
import com.logistica.patterns.builder.EnvioBuilder;
import com.logistica.patterns.builder.EnvioDirector;
import com.logistica.patterns.composite.ParadaEntrega;
import com.logistica.patterns.composite.RutaCompuesta;
import com.logistica.patterns.decorator.EmpaqueReforzadoDecorator;
import com.logistica.patterns.decorator.IServicioEnvioDecorable;
import com.logistica.patterns.decorator.RastreoSMSDecorator;
import com.logistica.patterns.decorator.SeguroPremiumDecorator;
import com.logistica.patterns.decorator.ServicioEnvioBase;
import com.logistica.patterns.facade.PlataformaLogisticaFacade;
import com.logistica.patterns.prototype.PlantillaEnvioManager;
import com.logistica.service.IEnvioService;
import com.logistica.service.INotificacionService;
import com.logistica.service.ITarifaCalculatorService;
import com.logistica.service.impl.EnvioServiceImpl;
import com.logistica.service.impl.NotificacionEmailService;
import com.logistica.service.impl.TarifaCalculatorServiceImpl;
import com.logistica.ui.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Scanner;

/**
 * Clase principal del sistema.
 * Permite ejecutar la aplicacion en modo Grafico (Swing)
 * o ejecutar la demostracion interactiva por Consola de todos
 * los patrones de diseno implementados (Avance 1 y Avance 2).
 */
public class AppLogistica {

    public static void main(String[] args) {
        if (args.length > 0 && "--gui".equalsIgnoreCase(args[0])) {
            iniciarModoGrafico();
            return;
        }

        System.out.println("=================================================================");
        System.out.println("    PLATAFORMA DE LOGISTICA Y SEGUIMIENTO DE ENVIOS (UTP 2026)   ");
        System.out.println("         Demostracion Integral de Patrones de Diseno GoF         ");
        System.out.println("=================================================================");
        System.out.println(" 1. Demostracion Completa de Patrones (Consola)");
        System.out.println(" 2. Iniciar Interfaz Grafica (Swing Dark Mode)");
        System.out.println(" 3. Salir");
        System.out.print("\nSeleccione una opcion [1-3] (Por defecto: 1): ");

        Scanner scanner = new Scanner(System.in);
        String opcion = "1";
        try {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    opcion = input;
                }
            }
        } catch (Exception ignored) {}

        switch (opcion) {
            case "2":
                iniciarModoGrafico();
                break;
            case "3":
                System.out.println("Cerrando aplicacion. Hasta pronto.");
                break;
            case "1":
            default:
                ejecutarDemostracionPatrones();
                break;
        }
    }

    private static void iniciarModoGrafico() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            IEnvioDAO envioDAO = new EnvioDAOMySQL();
            ITarifaCalculatorService tarifaService = new TarifaCalculatorServiceImpl();
            INotificacionService notifService = new NotificacionEmailService();
            IEnvioService envioService = new EnvioServiceImpl(envioDAO, tarifaService, notifService);
            EnvioController envioController = new EnvioController(envioService);
            TrackingController trackingController = new TrackingController(envioService);

            VentanaPrincipal ventana = new VentanaPrincipal(envioController, trackingController);
            ventana.setVisible(true);
        });
    }

    public static void ejecutarDemostracionPatrones() {
        System.out.println("\n=================================================================");
        System.out.println("     INICIANDO DEMOSTRACION DE PATRONES DE DISENO (GoF)          ");
        System.out.println("=================================================================");

        demostrarPrototype();
        demostrarBuilder();
        demostrarAbstractFactory();
        demostrarAdapter();
        demostrarDecorator();
        demostrarComposite();
        demostrarFacade();

        System.out.println("\n=================================================================");
        System.out.println("   DEMOSTRACION FINALIZADA CON EXITO - TODOS LOS PATRONES OK     ");
        System.out.println("=================================================================\n");
    }

    // 1. PROTOTYPE
    private static void demostrarPrototype() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  1. PATRON PROTOTYPE - Clonacion de Plantillas de Envio         ");
        System.out.println("-----------------------------------------------------------------");

        PlantillaEnvioManager manager = new PlantillaEnvioManager();

        // Registrar prototipos base
        Envio prototipoLima = new Envio("TEMP-001", "EXPRESS", 1, "Cliente Frecuente S.A.C.",
                "Av. Canaval y Moreyra 450, San Isidro", "999888777", "contacto@cliente.com", 2.5, 35.00);
        Envio prototipoArequipa = new Envio("TEMP-002", "INTERPROVINCIAL", 1, "Sucursal Arequipa",
                "Av. Ejercito 101, Yanahuara", "988776655", "arequipa@sucursal.com", 10.0, 75.00);

        manager.registrarPlantilla("CORPORATIVO_LIMA", prototipoLima);
        manager.registrarPlantilla("INTERPROVINCIAL_AREQUIPA", prototipoArequipa);

        manager.listarPlantillas();

        System.out.println("\n--- Clonando plantilla CORPORATIVO_LIMA ---");
        Envio envioClonado = manager.clonarPlantilla("CORPORATIVO_LIMA");
        envioClonado.setCodigoTracking("TRK-CORP-2026-001");
        envioClonado.setNombreDestinatario("Empresa ABC S.A.C.");
        envioClonado.setDireccionDestino("Av. Javier Prado Este 2200, San Borja");

        System.out.println("  Envio clonado y personalizado:");
        System.out.println("    Tracking asignado: " + envioClonado.getCodigoTracking());
        System.out.println("    Destinatario:      " + envioClonado.getNombreDestinatario());
        System.out.println("    Direccion Destino: " + envioClonado.getDireccionDestino());
        System.out.println("    Servicio copiado:  " + envioClonado.getTipoServicio());
        System.out.println("    Costo clonado:     S/ " + envioClonado.getCostoTotal());
    }

    // 2. BUILDER
    private static void demostrarBuilder() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  2. PATRON BUILDER - Construccion Paso a Paso de Envios         ");
        System.out.println("-----------------------------------------------------------------");

        System.out.println("--- Construccion fluida y directa via EnvioBuilder ---");
        Envio envioPersonalizado = new EnvioBuilder()
            .conTipoServicio("EXPRESS")
            .conRemitente(101)
            .conDestinatario("Carlos Mendoza", "Av. Arequipa 1234, Lima")
            .conContacto("987654321", "carlos@gmail.com")
            .conPeso(4.5)
            .conCosto(45.50)
            .conSeguro(150.00)
            .conEmpaqueReforzado()
            .conInstrucciones("Fragil - Manipular con cuidado")
            .conHorarioPreferido("14:00 - 18:00")
            .conPrioridad(1)
            .build();

        System.out.println("  Envio construido: " + envioPersonalizado.getCodigoTracking() +
            " | " + envioPersonalizado.getNombreDestinatario() +
            " | Peso: " + envioPersonalizado.getPesoKg() + "kg" +
            " | S/ " + envioPersonalizado.getCostoTotal());

        System.out.println("\n--- Construccion estandarizada via EnvioDirector ---");
        EnvioDirector director = new EnvioDirector();
        Envio envioExpress = director.crearEnvioExpress(202, "Maria Garcia", "Av. Javier Prado 500", "maria@gmail.com", 2.0);
        System.out.println("  [Director] Envio Express creado: " + envioExpress.getCodigoTracking() +
            " | S/ " + envioExpress.getCostoTotal());

        Envio envioCorp = director.crearEnvioCorporativo(303, "Roberto Gomez", "Calle Real 789, Huancayo", "955443322", "roberto@empresa.com", 15.0, "Entregar en almacen central");
        System.out.println("  [Director] Envio Corporativo creado: " + envioCorp.getCodigoTracking() +
            " | S/ " + envioCorp.getCostoTotal());
    }

    // 3. ABSTRACT FACTORY
    private static void demostrarAbstractFactory() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  3. PATRON ABSTRACT FACTORY - Familias de Servicios Logisticos  ");
        System.out.println("-----------------------------------------------------------------");

        System.out.println("\n--- Familia 1: Logistica Urbana (Local) ---");
        ejecutarFamiliaLogistica(new LogisticaUrbanaFactory(), "Miraflores", "San Isidro");

        System.out.println("\n--- Familia 2: Logistica Interprovincial (Nacional) ---");
        ejecutarFamiliaLogistica(new LogisticaInterprovincialFactory(), "Lima Central", "Arequipa Terminal");
    }

    private static void ejecutarFamiliaLogistica(ILogisticaFactory factory, String origen, String destino) {
        ITransporteLogistico transporte = factory.crearTransporte();
        IEmpaque empaque = factory.crearEmpaque();
        IRutaLogistica ruta = factory.crearRuta();

        System.out.println("  * Transporte: " + transporte.getTipo() + " (Capacidad: " + transporte.getCapacidadKg() + " kg)");
        System.out.println("  * Empaque: " + empaque.getTipoEmpaque() + " (Costo: S/ " + empaque.getCostoAdicional() + ")");
        System.out.println("  * Ruta asignada: " + ruta.getTipoRuta() + " (Distancia est: " + ruta.getDistanciaEstimadaKm() + " km)");
        transporte.asignarRuta(origen, destino);
        empaque.empacar("TRK-DEMO-001");
        ruta.planificar(origen, destino);
    }

    // 4. ADAPTER
    private static void demostrarAdapter() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  4. PATRON ADAPTER - Integracion GPS Externa (Google Maps)      ");
        System.out.println("-----------------------------------------------------------------");

        IGeolocalizador geo = new GoogleMapsAdapter();

        System.out.println("\n--- Geolocalizar direccion de destino ---");
        double[] coordenadas = geo.obtenerCoordenadas("Av. Javier Prado 1234, San Isidro, Lima");
        System.out.printf("  Resultado: Lat=%.6f, Lng=%.6f%n", coordenadas[0], coordenadas[1]);

        System.out.println("\n--- Calcular distancia entre origen y destino ---");
        double distancia = geo.calcularDistancia(
            "Almacen Central - Av. Argentina, Lima",
            "Jr. Cusco 456, Miraflores, Lima"
        );
        System.out.printf("  Distancia calculada: %.2f km%n", distancia);
    }

    // 5. DECORATOR
    private static void demostrarDecorator() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  5. PATRON DECORATOR - Servicios Adicionales Dinamicos          ");
        System.out.println("-----------------------------------------------------------------");

        IServicioEnvioDecorable servicio = new ServicioEnvioBase("EXPRESS", 25.00);
        System.out.printf("%n  [BASE] %s -> S/ %.2f%n", servicio.getDescripcion(), servicio.calcularCosto());

        servicio = new SeguroPremiumDecorator(servicio, 1000.00);
        System.out.printf("  [+SEGURO] %s -> S/ %.2f%n", servicio.getDescripcion(), servicio.calcularCosto());

        servicio = new EmpaqueReforzadoDecorator(servicio);
        System.out.printf("  [+EMPAQUE] %s -> S/ %.2f%n", servicio.getDescripcion(), servicio.calcularCosto());

        servicio = new RastreoSMSDecorator(servicio);
        System.out.printf("  [+SMS] %s -> S/ %.2f%n", servicio.getDescripcion(), servicio.calcularCosto());

        System.out.printf("%n  TOTAL FINAL: S/ %.2f%n", servicio.calcularCosto());
        System.out.println("  Descripcion completa: " + servicio.getDescripcion());
    }

    // 6. COMPOSITE
    private static void demostrarComposite() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  6. PATRON COMPOSITE - Rutas Jerarquicas de Distribucion        ");
        System.out.println("-----------------------------------------------------------------\n");

        ParadaEntrega p1 = new ParadaEntrega("Av. Larco 200, Miraflores", "TRK-001", 3.5);
        ParadaEntrega p2 = new ParadaEntrega("Jr. Union 450, Centro", "TRK-002", 2.0);
        ParadaEntrega p3 = new ParadaEntrega("Av. Brasil 890, Pueblo Libre", "TRK-003", 4.2);
        ParadaEntrega p4 = new ParadaEntrega("Av. Tupac Amaru 1500, Comas", "TRK-004", 8.0);
        ParadaEntrega p5 = new ParadaEntrega("Av. Proceres 300, SJL", "TRK-005", 10.5);

        RutaCompuesta rutaSur = new RutaCompuesta("Lima Sur");
        rutaSur.agregar(p1);
        rutaSur.agregar(p2);

        RutaCompuesta rutaCentro = new RutaCompuesta("Lima Centro");
        rutaCentro.agregar(p3);

        RutaCompuesta rutaNorte = new RutaCompuesta("Lima Norte");
        rutaNorte.agregar(p4);
        rutaNorte.agregar(p5);

        RutaCompuesta rutaMetropolitana = new RutaCompuesta("Lima Metropolitana - Jornada Completa");
        rutaMetropolitana.agregar(rutaSur);
        rutaMetropolitana.agregar(rutaCentro);
        rutaMetropolitana.agregar(rutaNorte);

        rutaMetropolitana.mostrarDetalle(0);
        System.out.printf("%n  Distancia TOTAL de la jornada: %.1f km%n", rutaMetropolitana.getDistanciaTotal());
    }

    // 7. FACADE
    private static void demostrarFacade() {
        System.out.println("\n-----------------------------------------------------------------");
        System.out.println("  7. PATRON FACADE - Flujo Completo Simplificado                 ");
        System.out.println("-----------------------------------------------------------------");

        PlataformaLogisticaFacade facade = new PlataformaLogisticaFacade();

        Envio envio = facade.registrarEnvioCompleto(
            "EXPRESS",
            1,
            "Ana Torres",
            "Av. Arequipa 2500, Lince",
            "998877665",
            "ana.torres@email.com",
            3.0,
            true,
            true,
            true
        );

        facade.simularCicloDeVida(envio);
    }
}