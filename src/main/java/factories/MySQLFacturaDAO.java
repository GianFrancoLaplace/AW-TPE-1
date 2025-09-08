package factories;

import dao.FacturaDAO;
import entities.Factura;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLFacturaDAO implements FacturaDAO {
    public void insertFacturas(List<Factura> facturas) {
        if (facturas == null || facturas.isEmpty()) {
            System.out.println("Lista de facturas vacía");
            return;
        }

        String sql = "INSERT IGNORE INTO Factura (idFactura, idCliente) VALUES (?, ?)";

        System.out.println("Insertando " + facturas.size() + " facturas...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            int batchSize = 100;
            int count = 0;

            for (Factura factura : facturas) {
                pstmt.setInt(1, factura.getIdFactura());
                pstmt.setInt(2, factura.getIdCliente());
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

            System.out.println("Proceso de inserción de facturas completado");

        } catch (SQLException e) {
            System.err.println("Error insertando facturas: " + e.getMessage());
        }
    }

    public int countFacturas() {
        String sql = "SELECT COUNT(*) as total FROM Factura";

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando facturas: " + e.getMessage());
        }

        return 0;
    }
}
