/**
 * Clase abstracta que representa cualquier artículo que se puede vender
 * en el menú de FideBurguesas (ya sea un Producto individual o un Combo).
 * No se puede instanciar directamente: solo a través de sus subclases.
 */
public abstract class ItemMenu {
    
    // Atributos comunes a todo lo que se vende (Producto y Combo)
    protected int id;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected boolean disponible;

    // Constructor: lo usan Producto y Combo mediante super(...)
    public ItemMenu(int id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = true; // por defecto, todo item nuevo está disponible
    }

    // Getters 
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Único setter compartido: activar/desactivar el item del menú
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Sobrescrito aquí; Producto y Combo pueden sobrescribirlo otra vez (polimorfismo)
    @Override
    public String toString() {
        return nombre + " - ₡" + precio;
    }
}