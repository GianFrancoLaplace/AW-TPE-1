package dao;

import dto.ProductoRecaudacionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO {
    private Connection connection;
    private static MySQLFacturaProductoDAO instance;

    public MySQLFacturaProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public static MySQLFacturaProductoDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new MySQLFacturaProductoDAO(connection);
        }
        return instance;
    }

    @Override
    public ProductoRecaudacionDTO productoMasRecaudado() {
        String sql =
                "SELECT fp.idProducto, p.nombre, SUM(cantidad * valor) AS recaudacion_total FROM factura_producto fp " +
                        "JOIN producto p USING (idProducto) " +
                        "GROUP BY p.idProducto, p.nombre ORDER BY recaudacion_total DESC LIMIT 1";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductoRecaudacionDTO(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("recaudacion_total")
                );
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Error obteniendo producto con mayor recaudación: " + e.getMessage());
            return null;
        }
    }

    public int countFacturasProductos() {
        String sql = "SELECT COUNT(*) as total FROM Factura_Producto";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando facturas-productos: " + e.getMessage());
        }

        return 0;
    }
}
