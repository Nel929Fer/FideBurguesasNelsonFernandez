import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Pantalla de gestión de productos y combos (historia 2).
 * Muestra Producto y Combo mezclados en la misma tabla, usando
 * polimorfismo (ambos son ItemMenu) más un instanceof solo para
 * decidir qué texto mostrar en la columna "Tipo".
 */
public class VentanaGestionProductos extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private static int siguienteIdProducto = 100; // IDs simples para esta pantalla
    private static int siguienteIdCombo = 200;

    public VentanaGestionProductos() {
        setTitle("FideBurguesas - Gestionar Productos y Combos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Gestionar Productos y Combos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla: columnas fijas, ninguna celda editable directo
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Precio", "Categoria", "Tipo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel de botones superior (Agregar Producto / Agregar Combo / Eliminar)
        JPanel panelBotonesArriba = new JPanel();
        JButton btnAgregarProducto = new JButton("Agregar Producto");
        JButton btnAgregarCombo = new JButton("Agregar Combo");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotonesArriba.add(btnAgregarProducto);
        panelBotonesArriba.add(btnAgregarCombo);
        panelBotonesArriba.add(btnEliminar);
        add(panelBotonesArriba, BorderLayout.SOUTH);

        JButton btnVolver = new JButton("Volver");
        JPanel panelVolver = new JPanel();
        panelVolver.add(btnVolver);
        add(panelVolver, BorderLayout.PAGE_END);

        btnAgregarProducto.addActionListener(e -> agregarProducto());
        btnAgregarCombo.addActionListener(e -> agregarCombo());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnVolver.addActionListener(e -> this.dispose());

        cargarTabla();
    }

    /**
     * Limpia la tabla y la vuelve a llenar recorriendo Productos y Combos.
     * Cada fila usa item.getNombre() y item.getPrecio(), heredados de ItemMenu:
     * no importa si es Producto o Combo, el código para leerlos es el mismo.
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0); // borra todas las filas

        for (Producto p : Sistema.getProductos()) {
            modeloTabla.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getCategoria(), "Producto"});
        }
        for (Combo c : Sistema.getCombos()) {
            modeloTabla.addRow(new Object[]{c.getNombre(), c.getPrecio(), "Combo", "Combo"});
        }
    }

    private void agregarProducto() {
        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
            if (nombre == null || nombre.isBlank()) return;

            String descripcion = JOptionPane.showInputDialog(this, "Descripcion:");
            String precioTexto = JOptionPane.showInputDialog(this, "Precio:");
            double precio = Double.parseDouble(precioTexto);
            String categoria = JOptionPane.showInputDialog(this, "Categoria:");

            Producto nuevo = new Producto(siguienteIdProducto++, nombre, descripcion, precio, categoria);
            Sistema.getProductos().add(nuevo);
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero valido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCombo() {
        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del combo:");
            if (nombre == null || nombre.isBlank()) return;

            String descripcion = JOptionPane.showInputDialog(this, "Descripcion:");
            String precioTexto = JOptionPane.showInputDialog(this, "Precio del combo:");
            double precio = Double.parseDouble(precioTexto);

            List<Producto> disponibles = Sistema.getProductos();
            if (disponibles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero agregá al menos un producto.");
                return;
            }

            // Lista de selección múltiple para elegir qué productos entran al combo
            JList<Producto> listaSeleccion = new JList<>(disponibles.toArray(new Producto[0]));
            listaSeleccion.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            int resultado = JOptionPane.showConfirmDialog(this, new JScrollPane(listaSeleccion),
                    "Seleccione los productos del combo", JOptionPane.OK_CANCEL_OPTION);

            if (resultado != JOptionPane.OK_OPTION) return;

            Combo nuevoCombo = new Combo(siguienteIdCombo++, nombre, descripcion, precio);
            for (Producto p : listaSeleccion.getSelectedValuesList()) {
                nuevoCombo.agregarProducto(p);
            }

            Sistema.getCombos().add(nuevoCombo);
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero valido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná una fila primero.");
            return;
        }

        int cantidadProductos = Sistema.getProductos().size();

        if (fila < cantidadProductos) {
            Sistema.getProductos().remove(fila);
        } else {
            Sistema.getCombos().remove(fila - cantidadProductos);
        }
        cargarTabla();
    }
}