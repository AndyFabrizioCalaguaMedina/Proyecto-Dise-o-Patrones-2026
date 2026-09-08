package com.logistica;

import com.logistica.controller.EnvioController;
import com.logistica.controller.TrackingController;
import com.logistica.dao.IEnvioDAO;
import com.logistica.dao.impl.EnvioDAOMySQL;
import com.logistica.service.IEnvioService;
import com.logistica.service.INotificacionService;
import com.logistica.service.ITarifaCalculatorService;
import com.logistica.service.impl.EnvioServiceImpl;
import com.logistica.service.impl.NotificacionEmailService;
import com.logistica.service.impl.TarifaCalculatorServiceImpl;
import com.logistica.ui.ConsolaView;
import com.logistica.ui.VentanaPrincipal;

import javax.swing.*;
import java.awt.*;

/**
 * Clase principal de arranque de la aplicación (Inversión de Control / Composición de Dependencias).
 * Admite ejecución gráfica minimalista (Swing) y ejecución demostrativa por consola.
 */
public class AppLogistica {

    public static void main(String[] args) {
        // 1. Instanciación de la capa DAO (Acceso a datos MySQL / XAMPP)
        IEnvioDAO envioDAO = new EnvioDAOMySQL();

        // 2. Instanciación de servicios base (SRP / OCP / DIP)
        ITarifaCalculatorService tarifaCalculator = new TarifaCalculatorServiceImpl();
        INotificacionService notificador = new NotificacionEmailService();

        // 3. Inyección de dependencias en el Servicio de Envíos
        IEnvioService envioService = new EnvioServiceImpl(envioDAO, tarifaCalculator, notificador);

        // 4. Instanciación de Controladores
        EnvioController envioController = new EnvioController(envioService);
        TrackingController trackingController = new TrackingController(envioService);

        // Verificar si se solicitó expresamente consola o si el entorno no tiene pantalla
        boolean soloConsola = GraphicsEnvironment.isHeadless() || (args.length > 0 && "--console".equalsIgnoreCase(args[0]));

        if (soloConsola) {
            ConsolaView view = new ConsolaView(envioController, trackingController);
            view.renderizarDemostracionCompleta();
        } else {
            // Iniciar Vista de Consola demostrativa
            ConsolaView view = new ConsolaView(envioController, trackingController);
            view.renderizarDemostracionCompleta();

            // Iniciar Interfaz Gráfica Minimalista Swing
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ignored) {}

                VentanaPrincipal ventana = new VentanaPrincipal(envioController, trackingController);
                ventana.setVisible(true);
            });
        }
    }
}
