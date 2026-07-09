/**
 * Representa una línea dentro de una Orden (ej: "2x Hamburguesa" = una línea).
 * "item" puede ser un Producto o un Combo — no importa cuál, porque
 * ambos son ItemMenu y ambos saben responder getPrecio() y getNombre().
 * Eso es polimorfismo: el mismo código sirve para los dos casos.
 */
public class DetalleOrden {

    private int id;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private ItemMenu item; // referencia polimórfica: Producto o Combo

    public DetalleOrden(int id, ItemMenu item, int cantidad) {
        this.id = id;
        this.item = item;
        this.cantidad = cantidad;
        // Guardamos el precio al momento de la venta.
        // Si mañana el Producto sube de precio, esta línea ya facturada no cambia.
        this.precioUnitario = item.getPrecio();
        this.subtotal = calcularSubtotal();
    }

    public double calcularSubtotal() {
        this.subtotal = precioUnitario * cantidad;
        return subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Un solo getter, en vez de getProducto() y getCombo() por separado
    public ItemMenu getItem() {
        return item;
    }

    @Override
    public String toString() {
        // item.getNombre() funciona igual sea Producto o Combo
        return cantidad + "x " + item.getNombre() + " - ₡" + subtotal;
    }
}