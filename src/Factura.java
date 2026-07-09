import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Se genera a partir de una Orden ya con productos agregados.
 * Calcula impuesto (13%, IVA de Costa Rica) y puede exportarse a un archivo.
 */
public class Factura {

    private static final double TASA_IMPUESTO = 0.13;

    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private double total;
    private double impuesto;
    private double totalConImpuesto;
    private Orden orden;
    private Usuario cajero;

    /**
     * El constructor recibe una Orden ya con productos.
     * calcularTotal() de Orden puede lanzar OrdenVaciaException,
     * así que Factura no se puede crear si la orden está vacía.
     */
    public Factura(int id, Orden orden, Usuario cajero) throws OrdenVaciaException {
        this.id = id;
        this.orden = orden;
        this.cajero = cajero;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.total = orden.calcularTotal();
        this.impuesto = calcularImpuesto();
        this.totalConImpuesto = calcularTotal();
    }

    public double calcularImpuesto() {
        this.impuesto = total * TASA_IMPUESTO;
        return impuesto;
    }

    public double calcularTotal() {
        this.totalConImpuesto = total + impuesto;
        return totalConImpuesto;
    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getTotalConImpuesto() {
        return totalConImpuesto;
    }
    public Orden getOrden() {
        return orden;
    }

    public java.time.LocalDate getFecha() {
        return fecha;
    }

    public java.time.LocalTime getHora() {
        return hora;
    }

    public Usuario getCajero() {
        return cajero;
    }

    /**
     * Exporta la factura a un archivo de texto plano (historia 6).
     * IOException es una excepción "checked": el compilador obliga
     * a quien llame este método a manejarla con try/catch.
     */
    public void generarArchivo() throws IOException {
        String nombreArchivo = "Factura_" + id + ".txt";

        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            escritor.write("=== FideBurguesas ===\n");
            escritor.write("Factura #" + id + "\n");
            escritor.write("Fecha: " + fecha + "  Hora: " + hora + "\n");
            escritor.write("Cajero: " + cajero.getNombre() + "\n\n");

            for (DetalleOrden detalle : orden.getListaDetalles()) {
                escritor.write(detalle.toString() + "\n");
            }

            escritor.write("\nSubtotal: ₡" + total + "\n");
            escritor.write("Impuesto (13%): ₡" + impuesto + "\n");
            escritor.write("Total: ₡" + totalConImpuesto + "\n");
        }
        // el "try-with-resources" cierra el FileWriter automáticamente al terminar
    }
}