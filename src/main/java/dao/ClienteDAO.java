package dao;

import entities.Cliente;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteDAO {

    public static void insertClientes(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Lista de clientes vacía");
            return;
        }

        String sql = "INSERT IGNORE INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

        System.out.println("Insertando " + clientes.size() + " clientes...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);
            int batchSize = 100;
            int count = 0;

            for (Cliente cliente : clientes) {
                pstmt.setInt(1, cliente.getIdCliente());
                pstmt.setString(2, cliente.getNombre());
                pstmt.setString(3, cliente.getEmail());
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

            System.out.println("Proceso de inserción de clientes completado");

        } catch (SQLException e) {
            System.err.println("Error insertando clientes: " + e.getMessage());
        }
    }

    public static int countClientes() {
        String sql = "SELECT COUNT(*) as total FROM Cliente";

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando clientes: " + e.getMessage());
        }

        return 0;
    }
}