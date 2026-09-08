package com.logistica.ui;

import com.logistica.controller.EnvioController;
import com.logistica.controller.TrackingController;
import com.logistica.dto.EnvioRegistroDTO;
import com.logistica.dto.RespuestaOperacionDTO;
import com.logistica.model.Envio;
import com.logistica.model.EstadoEnvio;
import com.logistica.patterns.singleton.ConfiguracionSistema;
import com.logistica.persistence.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Ventana Principal de la Plataforma de Logística.
 * Interfaz gráfica moderna, minimalista y funcional construida con Java Swing.
 */
public class VentanaPrincipal extends JFrame {

    private final EnvioController envioController;
    private final TrackingController trackingController;

    // Componentes del Formulario de Registro
    private JComboBox<String> cbServicio;
    private JTextField txtDestinatario;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JSpinner spPeso;
    private JSpinner spDistancia;
    private JTextArea txtResumenRegistro;

    // Componentes de la pestaña Tracking (Observer)
    private JTextField txtBuscarTracking;
    private JLabel lblEstadoActual;
    private JLabel lblDetallePaquete;
    private JTextArea txtLogObserver;
    private String trackingSeleccionado;

    // Componentes de la Tabla
    private JTable tablaEnvios;
    private DefaultTableModel modeloTabla;

    public VentanaPrincipal(EnvioController envioController, TrackingController trackingController) {
        this.envioController = envioController;
        this.trackingController = trackingController;

        initUI();
        cargarDatosTabla();
    }

    private void initUI() {
        setTitle("ServiLogística Express | Plataforma de Envíos y Trazabilidad");
        setSize(950, 680);
        setMinimumSize(new Dimension(850, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());

        // 1. Barra Superior (Header Minimalista)
        add(crearHeader(), BorderLayout.NORTH);

        // 2. Contenedor con Pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Theme.BG_DARK);
        tabbedPane.setForeground(Theme.TEXT_MAIN);
        tabbedPane.setFont(Theme.FONT_BOLD);

        tabbedPane.addTab("  📦 Registrar Envío  ", crearPanelRegistro());
        tabbedPane.addTab("  📍 Seguimiento en Vivo (Observer)  ", crearPanelTracking());
        tabbedPane.addTab("  📋 Directorio de Envíos  ", crearPanelListado());
        tabbedPane.addTab("  🧩 Arquitectura & SOLID  ", crearPanelArquitectura());

        add(tabbedPane, BorderLayout.CENTER);

        // 3. Barra de Estado Inferior
        add(crearFooter(), BorderLayout.SOUTH);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.BG_DARK);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
                BorderFactory.createEmptyBorder(16, 24, 16, 24)
        ));

        JPanel pnlTitulos = new JPanel(new GridLayout(2, 1, 2, 2));
        pnlTitulos.setOpaque(false);

        JLabel lblTitulo = new JLabel("ServiLogística Nacional");
        lblTitulo.setFont(Theme.FONT_TITLE);
        lblTitulo.setForeground(Theme.TEXT_MAIN);

        JLabel lblSub = new JLabel("Plataforma de Gestión y Trazabilidad en Tiempo Real • UTP 2026 (Avance 1)");
        lblSub.setFont(Theme.FONT_SMALL);
        lblSub.setForeground(Theme.TEXT_MUTED);

        pnlTitulos.add(lblTitulo);
        pnlTitulos.add(lblSub);
        header.add(pnlTitulos, BorderLayout.WEST);

        // Indicador de Estado de Conexión
        boolean mysqlOk = verificarConexionMySQL();
        JLabel badgeBD = new JLabel(mysqlOk ? "● MySQL XAMPP Conectado" : "● Modo Local (Memoria)");
        badgeBD.setFont(Theme.FONT_SMALL);
        badgeBD.setForeground(mysqlOk ? Theme.SUCCESS : Theme.WARNING);
        badgeBD.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        header.add(badgeBD, BorderLayout.EAST);

        return header;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel gridForm = new JPanel(new GridLayout(7, 2, 12, 10));
        gridForm.setBackground(Theme.BG_CARD);
        gridForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        // Campos
        gridForm.add(Theme.crearEtiqueta("Tipo de Servicio:"));
        cbServicio = new JComboBox<>(new String[]{"EXPRESS", "ESTANDAR", "PROGRAMADO", "INTERPROVINCIAL"});
        cbServicio.setBackground(Theme.BG_INPUT);
        cbServicio.setForeground(Theme.TEXT_MAIN);
        cbServicio.setFont(Theme.FONT_REGULAR);
        gridForm.add(cbServicio);

        gridForm.add(Theme.crearEtiqueta("Nombre del Destinatario:"));
        txtDestinatario = Theme.crearCampoTexto();
        txtDestinatario.setText("Valeria Ramos");
        gridForm.add(txtDestinatario);

        gridForm.add(Theme.crearEtiqueta("Dirección de Entrega:"));
        txtDireccion = Theme.crearCampoTexto();
        txtDireccion.setText("Av. Javier Prado Este 2500, San Borja");
        gridForm.add(txtDireccion);

        gridForm.add(Theme.crearEtiqueta("Teléfono Destinatario:"));
        txtTelefono = Theme.crearCampoTexto();
        txtTelefono.setText("966444555");
        gridForm.add(txtTelefono);

        gridForm.add(Theme.crearEtiqueta("Email Destinatario:"));
        txtEmail = Theme.crearCampoTexto();
        txtEmail.setText("valeria.ramos@gmail.com");
        gridForm.add(txtEmail);

        gridForm.add(Theme.crearEtiqueta("Peso del Paquete (Kg):"));
        spPeso = new JSpinner(new SpinnerNumberModel(2.5, 0.1, 50.0, 0.5));
        spPeso.setFont(Theme.FONT_REGULAR);
        gridForm.add(spPeso);

        gridForm.add(Theme.crearEtiqueta("Distancia Estimada (Km):"));
        spDistancia = new JSpinner(new SpinnerNumberModel(15.0, 1.0, 1000.0, 5.0));
        spDistancia.setFont(Theme.FONT_REGULAR);
        gridForm.add(spDistancia);

        panel.add(gridForm, BorderLayout.NORTH);

        // Panel de Botón y Resultado
        JPanel pnlInferior = new JPanel(new BorderLayout(10, 10));
        pnlInferior.setOpaque(false);

        JButton btnRegistrar = Theme.crearBoton("Crear y Despachar Envío (Factory Method)", Theme.ACCENT);
        btnRegistrar.addActionListener(e -> registrarEnvioDesdeForm());
        pnlInferior.add(btnRegistrar, BorderLayout.NORTH);

        txtResumenRegistro = new JTextArea(4, 40);
        txtResumenRegistro.setEditable(false);
        txtResumenRegistro.setBackground(Theme.BG_INPUT);
        txtResumenRegistro.setForeground(Theme.TEXT_MAIN);
        txtResumenRegistro.setFont(Theme.FONT_MONO);
        txtResumenRegistro.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        txtResumenRegistro.setText("Complete los datos y presione 'Crear y Despachar Envío' para generar la guía y calcular el costo.");

        pnlInferior.add(new JScrollPane(txtResumenRegistro), BorderLayout.CENTER);
        panel.add(pnlInferior, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelTracking() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // Barra de Búsqueda
        JPanel pnlBusqueda = new JPanel(new BorderLayout(10, 0));
        pnlBusqueda.setOpaque(false);

        txtBuscarTracking = Theme.crearCampoTexto();
        txtBuscarTracking.setText("TRK-");
        JButton btnBuscar = Theme.crearBoton("Buscar Guía", Theme.ACCENT);
        btnBuscar.addActionListener(e -> buscarTracking(txtBuscarTracking.getText().trim()));

        pnlBusqueda.add(txtBuscarTracking, BorderLayout.CENTER);
        pnlBusqueda.add(btnBuscar, BorderLayout.EAST);
        panel.add(pnlBusqueda, BorderLayout.NORTH);

        // Panel Central: Estado y Botones de Transición
        JPanel pnlCentro = new JPanel(new BorderLayout(12, 12));
        pnlCentro.setOpaque(false);

        JPanel cardInfo = Theme.crearCard();
        cardInfo.setLayout(new GridLayout(2, 1, 6, 6));

        lblDetallePaquete = new JLabel("Seleccione o busque una guía para monitorear.");
        lblDetallePaquete.setFont(Theme.FONT_SUBTITLE);
        lblDetallePaquete.setForeground(Theme.TEXT_MAIN);

        lblEstadoActual = new JLabel("ESTADO: EN ESPERA");
        lblEstadoActual.setFont(Theme.FONT_TITLE);
        lblEstadoActual.setForeground(Theme.ACCENT);

        cardInfo.add(lblDetallePaquete);
        cardInfo.add(lblEstadoActual);
        pnlCentro.add(cardInfo, BorderLayout.NORTH);

        // Botones de Simulación de Transición (Patrón Observer)
        JPanel pnlBotonesEstados = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        pnlBotonesEstados.setOpaque(false);
        pnlBotonesEstados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.BORDER), "Transición Reactiva de Estados (Patrón Observer)",
                0, 0, Theme.FONT_BOLD, Theme.TEXT_MUTED));

        JButton btnRecogido = Theme.crearBoton("1. Recogido", Theme.BG_CARD);
        JButton btnClasificado = Theme.crearBoton("2. Clasificado", Theme.BG_CARD);
        JButton btnEnRuta = Theme.crearBoton("3. En Ruta", Theme.BG_CARD);
        JButton btnObservado = Theme.crearBoton("4. Observado", Theme.DANGER);
        JButton btnEntregado = Theme.crearBoton("5. Entregado", Theme.SUCCESS);

        btnRecogido.addActionListener(e -> transicionarEstado(EstadoEnvio.RECOGIDO, "Almacén", "Paquete recogido en origen"));
        btnClasificado.addActionListener(e -> transicionarEstado(EstadoEnvio.CLASIFICADO, "Hub Logístico", "Clasificado en faja automática"));
        btnEnRuta.addActionListener(e -> transicionarEstado(EstadoEnvio.EN_RUTA, "En Vehículo Reparto", "En móvil de última milla hacia destino"));
        btnObservado.addActionListener(e -> transicionarEstado(EstadoEnvio.OBSERVADO, "Dirección Destino", "Destinatario no responde llamada"));
        btnEntregado.addActionListener(e -> transicionarEstado(EstadoEnvio.ENTREGADO, "Dirección Destino", "Entregado a destinatario con firma digital"));

        pnlBotonesEstados.add(btnRecogido);
        pnlBotonesEstados.add(btnClasificado);
        pnlBotonesEstados.add(btnEnRuta);
        pnlBotonesEstados.add(btnObservado);
        pnlBotonesEstados.add(btnEntregado);

        pnlCentro.add(pnlBotonesEstados, BorderLayout.CENTER);

        // Bitácora de Observadores
        txtLogObserver = new JTextArea(8, 40);
        txtLogObserver.setEditable(false);
        txtLogObserver.setBackground(Theme.BG_INPUT);
        txtLogObserver.setForeground(Theme.TEXT_MAIN);
        txtLogObserver.setFont(Theme.FONT_MONO);
        txtLogObserver.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollLog = new JScrollPane(txtLogObserver);
        scrollLog.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.BORDER), "Eventos Recibidos por los Observadores (Destinatario, Monitoreo y Auditoría)",
                0, 0, Theme.FONT_SMALL, Theme.TEXT_MUTED));

        pnlCentro.add(scrollLog, BorderLayout.SOUTH);
        panel.add(pnlCentro, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        String[] columnas = {"Código Tracking", "Servicio", "Destinatario", "Teléfono", "Peso (Kg)", "Costo (S/)", "Estado Actual"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaEnvios = new JTable(modeloTabla);
        tablaEnvios.setFont(Theme.FONT_REGULAR);
        tablaEnvios.setForeground(Theme.TEXT_MAIN);
        tablaEnvios.setBackground(Theme.BG_CARD);
        tablaEnvios.setGridColor(Theme.BORDER);
        tablaEnvios.setRowHeight(28);
        tablaEnvios.getTableHeader().setFont(Theme.FONT_BOLD);
        tablaEnvios.getTableHeader().setBackground(Theme.BG_INPUT);
        tablaEnvios.getTableHeader().setForeground(Theme.TEXT_MAIN);

        // Centrar celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaEnvios.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablaEnvios.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tablaEnvios.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(tablaEnvios);
        scroll.getViewport().setBackground(Theme.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón de Recarga
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlAcciones.setOpaque(false);
        JButton btnRefrescar = Theme.crearBoton("Actualizar Tabla", Theme.ACCENT);
        btnRefrescar.addActionListener(e -> cargarDatosTabla());
        pnlAcciones.add(btnRefrescar);
        panel.add(pnlAcciones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelArquitectura() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 16, 16));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        panel.add(crearCardArquitectura("SRP - Responsabilidad Única",
                "• TarifaCalculator: Únicamente calcula costos.\n• EnvioDAO: Únicamente persiste en MySQL.\n• EnvioService: Orquesta el negocio."));

        panel.add(crearCardArquitectura("OCP - Abierto / Cerrado",
                "• Notificaciones extensibles (Email, WhatsApp) mediante INotificacionService.\n• Servicios de envío extensibles mediante EnvioFactory."));

        panel.add(crearCardArquitectura("LSP - Sustitución de Liskov",
                "• Jerarquía de Transporte: Camión y Bicicleta heredan limpiamente sin lanzar excepciones de métodos inexistentes."));

        panel.add(crearCardArquitectura("ISP - Segregación de Interfaces",
                "• Interfaces específicas por actor: IOperadorAlmacenService e IRepartidorRutaService sin métodos innecesarios."));

        panel.add(crearCardArquitectura("DIP - Inversión de Dependencias",
                "• EnvioServiceImpl depende únicamente de abstracciones (IEnvioDAO, INotificacionService) inyectadas en el constructor."));

        panel.add(crearCardArquitectura("Patrones GoF Implementados",
                "• Singleton: ConexionBD y ConfiguracionSistema.\n• Factory Method: Creación de Envíos.\n• Observer: Trazabilidad y notificaciones reactivas."));

        return panel;
    }

    private JPanel crearCardArquitectura(String titulo, String contenido) {
        JPanel card = Theme.crearCard();
        card.setLayout(new BorderLayout(8, 8));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(Theme.FONT_SUBTITLE);
        lblTit.setForeground(Theme.ACCENT);
        card.add(lblTit, BorderLayout.NORTH);

        JTextArea txt = new JTextArea(contenido);
        txt.setEditable(false);
        txt.setOpaque(false);
        txt.setFont(Theme.FONT_REGULAR);
        txt.setForeground(Theme.TEXT_MAIN);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        card.add(txt, BorderLayout.CENTER);

        return card;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Theme.BG_DARK);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));

        ConfiguracionSistema config = ConfiguracionSistema.getInstancia();
        JLabel lblInfo = new JLabel(config.getNombreEmpresa() + " | RUC: " + config.getRuc() + " | v" + config.getVersion());
        lblInfo.setFont(Theme.FONT_SMALL);
        lblInfo.setForeground(Theme.TEXT_MUTED);

        JLabel lblAutor = new JLabel("Curso: Diseño de Patrones | UTP");
        lblAutor.setFont(Theme.FONT_SMALL);
        lblAutor.setForeground(Theme.TEXT_MUTED);

        footer.add(lblInfo, BorderLayout.WEST);
        footer.add(lblAutor, BorderLayout.EAST);
        return footer;
    }

    // =========================================================
    // LÓGICA DE CONTROL Y ACCIONES DE LA VISTA
    // =========================================================

    private void registrarEnvioDesdeForm() {
        try {
            String servicio = (String) cbServicio.getSelectedItem();
            String destinatario = txtDestinatario.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String email = txtEmail.getText().trim();
            double peso = (Double) spPeso.getValue();
            double distancia = (Double) spDistancia.getValue();

            if (destinatario.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete el nombre y la dirección.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            EnvioRegistroDTO dto = new EnvioRegistroDTO(servicio, 1, destinatario, direccion, telefono, email, peso, distancia);
            RespuestaOperacionDTO resp = envioController.registrarEnvio(dto);

            if (resp.isExito() && resp.getData() instanceof Envio) {
                Envio e = (Envio) resp.getData();
                this.trackingSeleccionado = e.getCodigoTracking();
                txtBuscarTracking.setText(trackingSeleccionado);

                txtResumenRegistro.setText(String.format("✅ ENVÍO REGISTRADO CON ÉXITO\n"
                                + "Código Tracking: %s\nServicio: %s\nDestinatario: %s\n"
                                + "Costo Calculado: S/ %.2f | Estado Inicial: %s\n"
                                + "Notificación enviada al correo: %s",
                        e.getCodigoTracking(), e.getTipoServicio(), e.getNombreDestinatario(),
                        e.getCostoTotal(), e.getEstadoActual(), e.getEmailDestinatario()));

                cargarDatosTabla();
                mostrarDetalleTracking(e);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar envío: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarTracking(String tracking) {
        if (tracking.isEmpty()) return;
        Optional<Envio> opt = envioController.buscarEnvio(tracking);
        if (opt.isPresent()) {
            this.trackingSeleccionado = opt.get().getCodigoTracking();
            mostrarDetalleTracking(opt.get());
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el envío con código: " + tracking, "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarDetalleTracking(Envio e) {
        lblDetallePaquete.setText("Guía: " + e.getCodigoTracking() + " | Destinatario: " + e.getNombreDestinatario() + " (" + e.getDireccionDestino() + ")");
        lblEstadoActual.setText("ESTADO ACTUAL: " + e.getEstadoActual().name());

        if (e.getEstadoActual() == EstadoEnvio.ENTREGADO) {
            lblEstadoActual.setForeground(Theme.SUCCESS);
        } else if (e.getEstadoActual() == EstadoEnvio.OBSERVADO) {
            lblEstadoActual.setForeground(Theme.DANGER);
        } else {
            lblEstadoActual.setForeground(Theme.ACCENT);
        }
    }

    private void transicionarEstado(EstadoEnvio nuevoEstado, String ubicacion, String observacion) {
        if (trackingSeleccionado == null || trackingSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero registre o busque un envío.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        RespuestaOperacionDTO resp = trackingController.actualizarEstado(trackingSeleccionado, nuevoEstado, ubicacion, observacion);
        if (resp.isExito()) {
            Envio e = (Envio) resp.getData();
            mostrarDetalleTracking(e);

            String hora = DateTimeFormatter.ofPattern("HH:mm:ss").format(java.time.LocalTime.now());
            String log = String.format("[%s] [OBSERVER EVENT] Guía %s -> %s | Obs: %s\n"
                            + "   -> Destinatario Notificado: \"%s\"\n"
                            + "   -> Dashboard Actualizado: Telemetría en %s\n"
                            + "   -> Auditoría: Registrado evento inmutable.\n\n",
                    hora, e.getCodigoTracking(), nuevoEstado, observacion,
                    nuevoEstado.getDescripcion(), ubicacion);

            txtLogObserver.append(log);
            cargarDatosTabla();
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<Envio> envios = envioController.listarEnvios();
        for (Envio e : envios) {
            modeloTabla.addRow(new Object[]{
                    e.getCodigoTracking(),
                    e.getTipoServicio(),
                    e.getNombreDestinatario(),
                    e.getTelefonoDestinatario(),
                    String.format("%.2f", e.getPesoKg()),
                    String.format("%.2f", e.getCostoTotal()),
                    e.getEstadoActual()
            });
        }
    }

    private boolean verificarConexionMySQL() {
        try (Connection c = ConexionBD.getInstancia().obtenerConexion()) {
            return c != null && !c.isClosed();
        } catch (Exception e) {
            return false;
        }
    }
}
