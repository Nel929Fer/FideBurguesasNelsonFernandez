import javax.swing.*;
import java.awt.*;

/**
 * Menú principal luego del login. Desde aquí se navega
 * a Gestión de Productos/Combos, Nueva Orden, o se cierra sesión.
 */
public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal() {
        setTitle("FideBurguesas - Menu Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Grilla 2x2, igual que el prototipo Figma
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnGestionProductos = new JButton("Gestionar Productos y Combos");
        JButton btnNuevaOrden = new JButton("Nueva Orden");
        JButton btnVerFacturas = new JButton("Ver Facturas");
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");

        btnGestionProductos.addActionListener(e -> {
            new VentanaGestionProductos().setVisible(true);
        });

        btnNuevaOrden.addActionListener(e -> {
            new VentanaNuevaOrden().setVisible(true);
        });

        btnVerFacturas.addActionListener(e -> {
            // Función simple por ahora: no es una de las 8 historias de usuario,
            // solo un botón de navegación del prototipo. La podemos ampliar después.
            JOptionPane.showMessageDialog(this, "Función disponible próximamente.");
        });

        btnCerrarSesion.addActionListener(e -> {
            Sistema.getUsuarioActual().cerrarSesion();
            Sistema.setUsuarioActual(null);
            new VentanaLogin().setVisible(true);
            this.dispose();
        });

        panelBotones.add(btnGestionProductos);
        panelBotones.add(btnNuevaOrden);
        panelBotones.add(btnVerFacturas);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.CENTER);
    }
}