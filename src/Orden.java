import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa el pedido completo de un cliente: una o varias líneas
 * (DetalleOrden), un estado, y el cajero que la creó.
 */
public class Orden {

    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado; // "PENDIENTE", "LISTA", "FACTURADA"
    private List<DetalleOrden> listaDetalles;
    private double total;
    private Usuario cajero;
    public java.time.LocalDate getFecha() {
    return fecha;
}

public java.time.LocalTime getHora() {
    return hora;
}

    public Orden(int id, Usuario cajero) {
        this.id = id;
        this.cajero = cajero;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.estado = "PENDIENTE";
        this.listaDetalles = new ArrayList<>();
        this.total = 0;
    }

    public void agregarDetalle(DetalleOrden detalle) {
        listaDetalles.add(detalle);
    }

    public void eliminarDetalle(DetalleOrden detalle) {
        listaDetalles.remove(detalle);
    }

    /**
     * Suma el subtotal de cada línea. Si la orden no tiene líneas,
     * no tiene sentido cobrar nada: lanzamos OrdenVaciaException.
     */
    public double calcularTotal() throws OrdenVaciaException {
        if (listaDetalles.isEmpty()) {
            throw new OrdenVaciaException("No se puede calcular el total de una orden sin productos.");
        }
        double suma = 0;
        for (DetalleOrden detalle : listaDetalles) {
            suma += detalle.getSubtotal();
        }
        this.total = suma;
        return total;
    }

    public List<DetalleOrden> getListaDetalles() {
        return listaDetalles;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Usuario getCajero() {
        return cajero;
    }
}