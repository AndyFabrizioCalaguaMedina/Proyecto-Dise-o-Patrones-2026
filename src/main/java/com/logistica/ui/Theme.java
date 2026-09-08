package com.logistica.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Paleta de estilos y temas minimalistas para la interfaz gráfica Swing.
 * Diseño limpio, moderno con estilo flat/minimalista.
 */
public class Theme {

    // Paleta de colores minimalista
    public static final Color BG_DARK = new Color(15, 23, 42);       // Slate 900
    public static final Color BG_CARD = new Color(30, 41, 59);       // Slate 800
    public static final Color BG_INPUT = new Color(51, 65, 85);      // Slate 700
    public static final Color TEXT_MAIN = new Color(248, 250, 252);  // Slate 50
    public static final Color TEXT_MUTED = new Color(148, 163, 184); // Slate 400
    public static final Color ACCENT = new Color(14, 165, 233);      // Sky 500
    public static final Color ACCENT_HOVER = new Color(56, 189, 248);
    public static final Color SUCCESS = new Color(34, 197, 94);     // Green 500
    public static final Color WARNING = new Color(234, 179, 8);      // Amber 500
    public static final Color DANGER = new Color(239, 68, 68);       // Red 500
    public static final Color BORDER = new Color(71, 85, 105);       // Slate 600

    // Tipografías limpias
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 11);

    public static JButton crearBoton(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg, 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        return btn;
    }

    public static JTextField crearCampoTexto() {
        JTextField txt = new JTextField();
        txt.setFont(FONT_REGULAR);
        txt.setForeground(TEXT_MAIN);
        txt.setBackground(BG_INPUT);
        txt.setCaretColor(TEXT_MAIN);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return txt;
    }

    public static JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_REGULAR);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    public static JPanel crearCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return card;
    }
}
