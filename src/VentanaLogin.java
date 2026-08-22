import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Primera pantalla de la aplicación: login del cajero.
 * Al iniciar sesión correctamente, guarda el usuario en Sistema
 * y abre VentanaMenuPrincipal.
 */
public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public VentanaLogin() {
        // Configuración básica de la ventana
        setTitle("FideBurguesas - Iniciar Sesion");
        setSize(350, 220);
        setLocationRelativeTo(null); // centra la ventana en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Título arriba
        JLabel lblTitulo = new JLabel("FideBurguesas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central con usuario y contraseña, en una grilla simple
        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 5, 10));
        panelCentro.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelCentro.add(txtUsuario);

        panelCentro.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelCentro.add(txtContrasena);
        add(panelCentro, BorderLayout.CENTER);

        // Botón abajo
        JButton btnIniciarSesion = new JButton("Iniciar Sesion");
        btnIniciarSesion.addActionListener(e -> intentarLogin());
        add(btnIniciarSesion, BorderLayout.SOUTH);
    }

    /**
     * Busca el usuario ingresado dentro de Sistema.getUsuarios()
     * y valida la contraseña usando el método iniciarSesion() de Usuario.
     */
    private void intentarLogin() {
        String usuarioIngresado = txtUsuario.getText();
        String contrasenaIngresada = new String(txtContrasena.getPassword());

        Usuario usuario = buscarUsuarioPorNombre(usuarioIngresado);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuario.iniciarSesion(usuarioIngresado, contrasenaIngresada);
            Sistema.setUsuarioActual(usuario);

            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre() + "!");

            new VentanaMenuPrincipal().setVisible(true);
            this.dispose(); // cierra la ventana de login

        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

private Usuario buscarUsuarioPorNombre(String nombreUsuario) {
    try {
        return UsuarioDAO.buscarPorUsuario(nombreUsuario);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error de conexion con la base de datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
}
}