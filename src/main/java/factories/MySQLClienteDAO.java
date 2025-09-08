package factories;
import dao.ClienteDAO;
import entities.Cliente;
import entities.ClienteFacturacion;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public void insertClientes(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Lista de clientes vacía");
            return;
        }

        String sql = "INSERT IGNORE INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

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
                }
            }

            if (count % batchSize != 0) {
                pstmt.executeBatch();
            }

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.err.println("Error insertando clientes: " + e.getMessage());
        }
    }

    @Override
    public int countClientes() {
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

    @Override
    public List<ClienteFacturacion> getClientesOrdenadosPorFacturacion() {
        String sql =
                "SELECT c.idCliente, c.nombre, c.email, " +
                        "SUM(fp.cantidad * p.valor) AS total_facturado " +
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
                clientes.add(new ClienteFacturacion(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getFloat("total_facturado")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo clientes ordenados por facturación: " + e.getMessage());
        }

        return clientes;
    }
}
