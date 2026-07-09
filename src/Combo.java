import java.util.ArrayList;
import java.util.List;

/**
 * Combo del menú (ej: Combo Clásico = Hamburguesa + Papas).
 * Hereda de ItemMenu igual que Producto, pero además contiene
 * una lista de Producto que forman parte del combo.
 */
public class Combo extends ItemMenu {

    // Colección genérica: un Combo agrupa varios Producto
    private List<Producto> listaProductos;

    public Combo(int id, String nombre, String descripcion, double precio) {
        super(id, nombre, descripcion, precio);
        // Inicializamos la lista vacía; se llena después con agregarProducto()
        this.listaProductos = new ArrayList<>();
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    // Agrega un producto existente al combo (ej: agregar "Hamburguesa" al Combo Clásico)
    public void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    // Mismo método toString() que en Producto, pero con comportamiento distinto:
    // aquí también listamos qué productos incluye el combo. Esto es polimorfismo:
    // si tenés una lista de ItemMenu con Productos y Combos mezclados,
    // cada uno imprime su propia versión al llamar item.toString().
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nombre + " - ₡" + precio + " [");
        for (int i = 0; i < listaProductos.size(); i++) {
            sb.append(listaProductos.get(i).getNombre());
            if (i < listaProductos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}