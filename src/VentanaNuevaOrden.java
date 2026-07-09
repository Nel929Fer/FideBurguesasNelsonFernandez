import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla de creación de orden (historias 3 y 4): elegir productos/combos,
 * agregarlos a la orden, y ver el total calculado automáticamente.
 */
public class VentanaNuevaOrden extends JFrame {

    private static int siguienteIdOrden = 1;
    private static int siguienteIdDetalle = 1;
    private static int siguienteIdFactura = 1;

    private Orden ordenActual;
    private List<ItemMenu> itemsDisponibles; // mapea filas de la tabla izquierda a objetos reales

    private DefaultTableModel modeloDisponibles;
    private DefaultTableModel modeloOrden;
    private JTable tablaDisponibles;
    private JTable tablaOrden;
    private JSpinner spinnerCantidad;
    private JLabel lblTotal;

    public VentanaNuevaOrden() {
        ordenActual = new Orden(siguienteIdOrden++, Sistema.getUsuarioActual());
        itemsDisponibles = new ArrayList<>();

        setTitle("FideBurguesas - Nueva Orden");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Nueva Orden", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel izquierdo: productos disponibles ---
        modeloDisponibles = new DefaultTableModel(new Object[]{"Nombre", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDisponibles = new JTable(modeloDisponibles);
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(new JLabel("Productos Disponibles", SwingConstants.CENTER), BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(tablaDisponibles), BorderLayout.CENTER);

        // --- Panel central: cantidad + botón agregar ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        JButton btnAgregar = new JButton("Agregar >>");
        panelCentro.add(new JLabel("Cantidad:"));
        panelCentro.add(spinnerCantidad);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(btnAgregar);

        // --- Panel derecho: orden actual ---
        modeloOrden = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaOrden = new JTable(modeloOrden);
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.add(new JLabel("Orden Actual", SwingConstants.CENTER), BorderLayout.NORTH);
        panelDerecho.add(new JScrollPane(tablaOrden), BorderLayout.CENTER);

        lblTotal = new JLabel("Total: ₡0", SwingConstants.RIGHT);
        panelDerecho.add(lblTotal, BorderLayout.SOUTH);

        // Unimos los tres paneles centrales
        JPanel panelListas = new JPanel(new GridLayout(1, 3, 10, 0));
        panelListas.add(panelIzquierdo);
        panelListas.add(panelCentro);
        panelListas.add(panelDerecho);
        add(panelListas, BorderLayout.CENTER);

        // --- Botones inferiores ---
        JButton btnConfirmar = new JButton("Confirmar Orden");
        JButton btnCancelar = new JButton("Cancelar");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarDetalle());
        btnConfirmar.addActionListener(e -> confirmarOrden());
        btnCancelar.addActionListener(e -> this.dispose());

        cargarProductosDisponibles();
    }

    // Junta Productos y Combos en una sola lista polimórfica (ItemMenu)
    private void cargarProductosDisponibles() {
        itemsDisponibles.clear();
        modeloDisponibles.setRowCount(0);

        for (Producto p : Sistema.getProductos()) {
            itemsDisponibles.add(p);
            modeloDisponibles.addRow(new Object[]{p.getNombre(), p.getPrecio()});
        }
        for (Combo c : Sistema.getCombos()) {
            itemsDisponibles.add(c);
            modeloDisponibles.addRow(new Object[]{c.getNombre(), c.getPrecio()});
        }
    }

    private void agregarDetalle() {
        int fila = tablaDisponibles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un producto o combo primero.");
            return;
        }

        ItemMenu item = itemsDisponibles.get(fila);
        int cantidad = (int) spinnerCantidad.getValue();

        DetalleOrden detalle = new DetalleOrden(siguienteIdDetalle++, item, cantidad);
        ordenActual.agregarDetalle(detalle);

        actualizarTablaOrden();
    }

    private void actualizarTablaOrden() {
        modeloOrden.setRowCount(0);
        for (DetalleOrden d : ordenActual.getListaDetalles()) {
            modeloOrden.addRow(new Object[]{d.getItem().getNombre(), d.getCantidad(), d.getSubtotal()});
        }

        try {
            double total = ordenActual.calcularTotal();
            lblTotal.setText("Total: ₡" + total);
        } catch (OrdenVaciaException ex) {
            lblTotal.setText("Total: ₡0");
        }
    }

    private void confirmarOrden() {
        try {
            ordenActual.calcularTotal();
            Sistema.getOrdenes().add(ordenActual);

            Factura factura = new Factura(siguienteIdFactura++, ordenActual, Sistema.getUsuarioActual());
            new VentanaFactura(factura).setVisible(true);
            this.dispose();

        } catch (OrdenVaciaException ex) {
            JOptionPane.showMessageDialog(this, "Agregá al menos un producto antes de confirmar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}