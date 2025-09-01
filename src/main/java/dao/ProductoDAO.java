package dao;

import entities.Producto;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductoDAO {

    public static void insertProductos(List<Producto> productos) {
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

    public static int countProductos() {
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
}