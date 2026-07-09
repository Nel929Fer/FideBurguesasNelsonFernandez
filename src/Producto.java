/**
 * Producto individual del menú (ej: Hamburguesa, Papas).
 * Hereda de ItemMenu: reutiliza id, nombre, descripcion, precio, disponible.
 */
public class Producto extends ItemMenu {

    private String categoria;

    public Producto(int id, String nombre, String descripcion, double precio, String categoria) {
        super(id, nombre, descripcion, precio);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return nombre + " (" + categoria + ") - ₡" + precio;
    }
}