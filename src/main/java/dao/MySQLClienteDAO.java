package dao;
import dto.ClienteFacturacionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLClienteDAO implements ClienteDAO {
    private Connection connection;
    private static MySQLClienteDAO instance;

    public MySQLClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public static MySQLClienteDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new MySQLClienteDAO(connection);
        }
        return instance;
    }


    @Override
    public int countClientes() {
        String sql = "SELECT COUNT(*) as total FROM Cliente";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando clientes: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public List<ClienteFacturacionDTO> getClientesOrdenadosPorFacturacion() {
        String sql =
                "SELECT c.idCliente, c.nombre, c.email, " +
                        "SUM(fp.cantidad * p.valor) AS total_facturado " +
                        "FROM Cliente c " +
                        "INNER JOIN Factura f ON c.idCliente = f.idCliente " +
                        "INNER JOIN Factura_Producto fp ON f.idFactura = fp.idFactura " +
                        "INNER JOIN Producto p ON fp.idProducto = p.idProducto " +
                        "GROUP BY c.idCliente, c.nombre, c.email " +
                        "ORDER BY total_facturado DESC";

        List<ClienteFacturacionDTO> clientes = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new ClienteFacturacionDTO(
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
