/**
 * Representa a un cajero o miembro del personal que usa el sistema.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String sucursal;
    private boolean sesionActiva;
    public int getId() {
    return id;
}

    public Usuario(int id, String nombre, String usuario, String contrasena, String sucursal) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.sucursal = sucursal;
        this.sesionActiva = false;
    }

    /**
     * Intenta iniciar sesión comparando las credenciales recibidas
     * contra las guardadas en este Usuario.
     * Si no coinciden, lanza CredencialesInvalidasException en vez de
     * devolver un booleano — así quien lo llama está obligado a manejar el error.
     */
    public void iniciarSesion(String usuarioIngresado, String contrasenaIngresada)
            throws CredencialesInvalidasException {

        if (!this.usuario.equals(usuarioIngresado) || !this.contrasena.equals(contrasenaIngresada)) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
        this.sesionActiva = true;
    }

    public void cerrarSesion() {
        this.sesionActiva = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }
}