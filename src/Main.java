import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Sistema.inicializarDatosDePrueba();
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}