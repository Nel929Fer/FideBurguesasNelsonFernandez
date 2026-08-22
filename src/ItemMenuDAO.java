import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemMenuDAO {

    public static List<Producto> listarProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM items_menu WHERE tipo = 'PRODUCTO'";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("descripcion"), rs.getDouble("precio"), rs.getString("categoria"));
                p.setDisponible(rs.getBoolean("disponible"));
                lista.add(p);
            }
        }
        return lista;
    }

    public static List<Combo> listarCombos() throws SQLException {
        List<Combo> lista = new ArrayList<>();
        String sql = "SELECT * FROM items_menu WHERE tipo = 'COMBO'";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Combo c = new Combo(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("descripcion"), rs.getDouble("precio"));
                c.setDisponible(rs.getBoolean("disponible"));
                cargarProductosDelCombo(conexion, c);
                lista.add(c);
            }
        }
        return lista;
    }

    private static void cargarProductosDelCombo(Connection conexion, Combo combo) throws SQLException {
        String sql = "SELECT p.* FROM items_menu p JOIN combo_producto cp ON cp.producto_id = p.id WHERE cp.combo_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, combo.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    combo.agregarProducto(new Producto(rs.getInt("id"), rs.getString("nombre"),
                            rs.getString("descripcion"), rs.getDouble("precio"), rs.getString("categoria")));
                }
            }
        }
    }

    public static int insertarProducto(Producto p) throws SQLException {
        String sql = "INSERT INTO items_menu (tipo, nombre, descripcion, precio, disponible, categoria) VALUES ('PRODUCTO', ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setDouble(3, p.getPrecio());
            stmt.setBoolean(4, p.isDisponible());
            stmt.setString(5, p.getCategoria());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : -1;
        }
    }
    public static void eliminar(int id) throws SQLException {
    String sql = "DELETE FROM items_menu WHERE id = ?";
    try (Connection conexion = ConexionBD.obtenerConexion();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}

    public static int insertarCombo(Combo c) throws SQLException {
        String sql = "INSERT INTO items_menu (tipo, nombre, descripcion, precio, disponible) VALUES ('COMBO', ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getDescripcion());
            stmt.setDouble(3, c.getPrecio());
            stmt.setBoolean(4, c.isDisponible());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            int comboId = keys.next() ? keys.getInt(1) : -1;

            String sqlRelacion = "INSERT INTO combo_producto (combo_id, producto_id) VALUES (?, ?)";
            try (PreparedStatement stmtRel = conexion.prepareStatement(sqlRelacion)) {
                for (Producto p : c.getListaProductos()) {
                    stmtRel.setInt(1, comboId);
                    stmtRel.setInt(2, p.getId());
                    stmtRel.executeUpdate();
                }
            }
            return comboId;
        }
    }
}