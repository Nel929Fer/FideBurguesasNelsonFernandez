import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

/**
 * Muestra la factura generada a partir de una Orden confirmada.
 * Permite exportarla a archivo de texto (historia 6).
 */
public class VentanaFactura extends JFrame {

    private Factura factura;

    public VentanaFactura(Factura factura) {
        this.factura = factura;

        setTitle("FideBurguesas - Factura");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Encabezado: numero de factura, fecha, cajero ---
        JPanel panelEncabezado = new JPanel(new GridLayout(3, 1));
        panelEncabezado.add(new JLabel("Factura: #" + factura.getId()));
        panelEncabezado.add(new JLabel("Fecha: " + factura.getFecha()));
        panelEncabezado.add(new JLabel("Cajero: " + factura.getCajero().getNombre()));
        add(panelEncabezado, BorderLayout.NORTH);

        // --- Tabla con las líneas de la orden ---
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (DetalleOrden d : factura.getOrden().getListaDetalles()) {
            modelo.addRow(new Object[]{d.getItem().getNombre(), d.getCantidad(), d.getSubtotal()});
        }
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- Pie: impuesto, total, y botones ---
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));

        JPanel panelTotales = new JPanel(new GridLayout(2, 1));
        panelTotales.add(new JLabel("Impuesto (13%): ₡" + factura.getImpuesto(), SwingConstants.RIGHT));
        panelTotales.add(new JLabel("Total: ₡" + factura.getTotalConImpuesto(), SwingConstants.RIGHT));
        panelInferior.add(panelTotales, BorderLayout.NORTH);

        JButton btnExportar = new JButton("Exportar Factura");
        JButton btnVolver = new JButton("Volver");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnExportar);
        panelBotones.add(btnVolver);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        btnExportar.addActionListener(e -> exportarFactura());
        btnVolver.addActionListener(e -> this.dispose());
    }

    private void exportarFactura() {
        try {
            factura.generarArchivo();
            JOptionPane.showMessageDialog(this, "Factura exportada correctamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}