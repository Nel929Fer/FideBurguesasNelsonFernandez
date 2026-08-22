import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class VentanaCocina extends JFrame {

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public VentanaCocina() {
        setTitle("FideBurguesas - Monitor de Cocina");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        modeloTabla = new DefaultTableModel(new Object[]{"Orden", "Detalle", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnMarcarLista = new JButton("Marcar Lista");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);
        panelBotones.add(btnMarcarLista);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> actualizarOrdenes());
        btnMarcarLista.addActionListener(e -> marcarListaSeleccionada());

        conectar();
        actualizarOrdenes();
    }

    private void conectar() {
        try {
            socket = new Socket("localhost", 6000);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor: " + e.getMessage());
        }
    }

    private void actualizarOrdenes() {
        try {
            salida.println("LISTAR");
            int cantidad = Integer.parseInt(entrada.readLine());

            modeloTabla.setRowCount(0);
            for (int i = 0; i < cantidad; i++) {
                String[] partes = entrada.readLine().split("\\|");
                modeloTabla.addRow(new Object[]{partes[0], partes[1], partes[2]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error consultando ordenes: " + e.getMessage());
        }
    }

    private void marcarListaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná una orden primero.");
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);

        try {
            salida.println("LISTA:" + id);
            entrada.readLine(); // respuesta "OK"
            actualizarOrdenes();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error marcando orden: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaCocina().setVisible(true));
    }
}