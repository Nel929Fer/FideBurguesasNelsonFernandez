/**
 * Excepción propia: se lanza cuando el usuario o la contraseña
 * ingresados no coinciden con los del sistema.
 * Extiende de Exception (no de RuntimeException) porque queremos
 * OBLIGAR a quien llame a iniciarSesion() a manejarla con try/catch.
 */
public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException(String mensaje) {
        // super(mensaje) guarda el mensaje para poder recuperarlo con getMessage()
        super(mensaje);
    }
}