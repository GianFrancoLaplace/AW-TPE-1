package dao;

import entities.Cliente;
import entities.ClienteFacturacion;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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

    public static List<ClienteFacturacion> getClientesOrdenadosPorFacturacion() {
        String sql =
                "SELECT " +
                        "    c.idCliente, " +
                        "    c.nombre, " +
                        "    c.email, " +
                        "    SUM(fp.cantidad * p.valor) AS total_facturado " +
                        "FROM Cliente c " +
                        "INNER JOIN Factura f ON c.idCliente = f.idCliente " +
                        "INNER JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
                        "INNER JOIN Producto p ON fp.idProducto = p.idProducto " +
                        "GROUP BY c.idCliente, c.nombre, c.email " +
                        "ORDER BY total_facturado DESC";

        List<ClienteFacturacion> clientes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteFacturacion cliente = new ClienteFacturacion(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getFloat("total_facturado")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo clientes ordenados por facturación: " + e.getMessage());
        }

        return clientes;
    }
}