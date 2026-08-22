import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public static Usuario buscarPorUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String usuario = resultado.getString("usuario");
                String contrasena = resultado.getString("contrasena");
                String sucursal = resultado.getString("sucursal");

                return new Usuario(id, nombre, usuario, contrasena, sucursal);
            }
            return null; // no se encontró
        }
    }
}