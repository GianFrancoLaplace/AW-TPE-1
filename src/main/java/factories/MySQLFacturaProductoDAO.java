package factories;

import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import entities.FacturaProducto;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO {
    public void insertFacturasProductos(List<FacturaProducto> facturasProductos) {
        if (facturasProductos == null || facturasProductos.isEmpty()) {
            System.out.println("Lista de facturas-productos vacía");
            return;
        }

        String sql = "INSERT IGNORE INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

        System.out.println("Insertando " + facturasProductos.size() + " relaciones factura-producto...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            int batchSize = 100;
            int count = 0;

            for (FacturaProducto fp : facturasProductos) {
                pstmt.setInt(1, fp.getIdFactura());
                pstmt.setInt(2, fp.getIdProducto());
                pstmt.setInt(3, fp.getCantidad());
                pstmt.addBatch();
                count++;

                if (count % batchSize == 0) {
                    pstmt.executeBatch();
                    System.out.println("   Batch " + (count/batchSize) + " ejecutado (" + count + "/" + facturasProductos.size() + ")");
                }
            }

            if (count % batchSize != 0) {
                pstmt.executeBatch();
            }

            conn.commit();
            conn.setAutoCommit(true);

            System.out.println("Proceso de inserción de facturas-productos completado");

        } catch (SQLException e) {
            System.err.println("Error insertando facturas-productos: " + e.getMessage());
        }
    }

    public int countFacturasProductos() {
        String sql = "SELECT COUNT(*) as total FROM Factura_Producto";

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando facturas-productos: " + e.getMessage());
        }

        return 0;
    }
}
