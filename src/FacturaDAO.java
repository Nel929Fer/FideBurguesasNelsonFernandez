import java.sql.*;

public class FacturaDAO {

    public static int guardarFactura(Factura factura, int ordenId) throws SQLException {
        String sql = "INSERT INTO facturas (orden_id, usuario_id, fecha, hora, total, impuesto, total_con_impuesto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ordenId);
            stmt.setInt(2, factura.getCajero().getId());
            stmt.setDate(3, Date.valueOf(factura.getFecha()));
            stmt.setTime(4, Time.valueOf(factura.getHora()));
            stmt.setDouble(5, factura.getTotal());
            stmt.setDouble(6, factura.getImpuesto());
            stmt.setDouble(7, factura.getTotalConImpuesto());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }
}