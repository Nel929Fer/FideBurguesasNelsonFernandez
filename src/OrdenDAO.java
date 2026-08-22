import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class OrdenDAO {

    public static int crearOrden(Orden orden) throws SQLException {
        String sqlOrden = "INSERT INTO ordenes (fecha, hora, estado, usuario_id) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_orden (orden_id, item_menu_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            int ordenId;
            try (PreparedStatement stmt = conexion.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDate(1, Date.valueOf(orden.getFecha()));
                stmt.setTime(2, Time.valueOf(orden.getHora()));
                stmt.setString(3, orden.getEstado());
                stmt.setInt(4, orden.getCajero().getId());
                stmt.executeUpdate();
                ResultSet keys = stmt.getGeneratedKeys();
                ordenId = keys.next() ? keys.getInt(1) : -1;
            }

            try (PreparedStatement stmt = conexion.prepareStatement(sqlDetalle)) {
                for (DetalleOrden d : orden.getListaDetalles()) {
                    stmt.setInt(1, ordenId);
                    stmt.setInt(2, d.getItem().getId());
                    stmt.setInt(3, d.getCantidad());
                    stmt.setDouble(4, d.getPrecioUnitario());
                    stmt.setDouble(5, d.getSubtotal());
                    stmt.executeUpdate();
                }
            }

            return ordenId;
        }
    }
    public static List<String> listarPendientesTexto() throws SQLException {
    List<String> resultado = new ArrayList<>();
    String sqlOrdenes = "SELECT id FROM ordenes WHERE estado = 'PENDIENTE' ORDER BY id";
    try (Connection conexion = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conexion.prepareStatement(sqlOrdenes);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            resultado.add(formatearOrden(conexion, rs.getInt("id")));
        }
    }
    return resultado;
}

private static String formatearOrden(Connection conexion, int ordenId) throws SQLException {
    String sqlDetalle = "SELECT im.nombre, d.cantidad, d.subtotal FROM detalle_orden d " +
                         "JOIN items_menu im ON im.id = d.item_menu_id WHERE d.orden_id = ?";
    StringBuilder items = new StringBuilder();
    double total = 0;
    try (PreparedStatement stmt = conexion.prepareStatement(sqlDetalle)) {
        stmt.setInt(1, ordenId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                if (items.length() > 0) items.append(", ");
                items.append(rs.getInt("cantidad")).append("x ").append(rs.getString("nombre"));
                total += rs.getDouble("subtotal");
            }
        }
    }
    return ordenId + "|" + items + "|" + total;
}

public static void marcarComoLista(int ordenId) throws SQLException {
    String sql = "UPDATE ordenes SET estado = 'LISTA' WHERE id = ?";
    try (Connection conexion = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, ordenId);
        stmt.executeUpdate();
    }
}
}