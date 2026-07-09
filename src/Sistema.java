import java.util.ArrayList;
import java.util.List;

/**
 * Contiene los datos compartidos mientras la aplicación está abierta:
 * usuarios registrados, productos, combos y órdenes creadas.
 * Las ventanas Swing consultan y modifican estas listas.
 */
public class Sistema {

    // static: existen una sola vez, compartidas por toda la aplicación
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<Combo> combos = new ArrayList<>();
    private static List<Orden> ordenes = new ArrayList<>();
    private static Usuario usuarioActual;

    // Se ejecuta una sola vez al iniciar el programa, con datos de ejemplo
    public static void inicializarDatosDePrueba() {
        usuarios.add(new Usuario(1, "Nelson Fernandez", "nelson", "1234", "Sede Central"));

        Producto hamburguesa = new Producto(1, "Hamburguesa", "Carne, queso, vegetales", 2500, "Principal");
        Producto papas = new Producto(2, "Papas", "Papas fritas medianas", 1000, "Acompañamiento");
        productos.add(hamburguesa);
        productos.add(papas);

        Combo comboClasico = new Combo(1, "Combo Clasico", "Hamburguesa + Papas", 5000);
        comboClasico.agregarProducto(hamburguesa);
        comboClasico.agregarProducto(papas);
        combos.add(comboClasico);
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static List<Producto> getProductos() {
        return productos;
    }

    public static List<Combo> getCombos() {
        return combos;
    }

    public static List<Orden> getOrdenes() {
        return ordenes;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }
}