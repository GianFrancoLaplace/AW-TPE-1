package factories;

import dao.ProductoDAO;
import entities.Producto;
import entities.ProductoRecaudacion;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLProductoDAO implements ProductoDAO {
    public void insertProductos(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            System.out.println("Lista de productos vacía");
            return;
        }

        String sql = "INSERT IGNORE INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";

        System.out.println("Insertando " + productos.size() + " productos...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            int batchSize = 100;
            int count = 0;

            for (Producto producto : productos) {
                pstmt.setInt(1, producto.getIdProducto());
                pstmt.setString(2, producto.getNombre());
                pstmt.setFloat(3, producto.getValor());
                pstmt.addBatch();
                count++;

                if (count % batchSize == 0) {
                    pstmt.executeBatch();
                    System.out.println("   Batch " + (count/batchSize) + " ejecutado");
                }
            }

            if (count % batchSize != 0) {
                pstmt.executeBatch();
            }

            conn.commit();
            conn.setAutoCommit(true);

            System.out.println("Proceso de inserción de productos completado");

        } catch (SQLException e) {
            System.err.println("Error insertando productos: " + e.getMessage());
        }
    }

    public int countProductos() {
        String sql = "SELECT COUNT(*) as total FROM Producto";

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando productos: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public ProductoRecaudacion productoMasRecaudado() {
        String sql =
                "SELECT " +
                        "    p.idProducto, " +
                        "    p.nombre, " +
                        "    p.valor, " +
                        "    SUM(fp.cantidad * p.valor) AS recaudacion_total " +
                        "FROM Producto p " +
                        "INNER JOIN Factura_Producto fp ON p.idProducto = fp.idProducto " +
                        "GROUP BY p.idProducto, p.nombre, p.valor " +
                        "ORDER BY recaudacion_total DESC " +
                        "LIMIT 1";

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new ProductoRecaudacion(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("valor"),
                        rs.getFloat("recaudacion_total")
                );
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Error obteniendo producto con mayor recaudación: " + e.getMessage());
            return null;
        }
    }
}
