/**
 * Se lanza cuando se intenta calcular el total o confirmar
 * una Orden que no tiene ningún DetalleOrden agregado.
 */
public class OrdenVaciaException extends Exception {

    public OrdenVaciaException(String mensaje) {
        super(mensaje);
    }
}