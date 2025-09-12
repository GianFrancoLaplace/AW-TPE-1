package dao;

import dto.ProductoRecaudacionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLProductoDAO implements ProductoDAO {
    private Connection connection;
    private static MySQLProductoDAO instance;

    public MySQLProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public static MySQLProductoDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new MySQLProductoDAO(connection);
        }
        return instance;
    }

    public int countProductos() {
        String sql = "SELECT COUNT(*) as total FROM Producto";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando productos: " + e.getMessage());
        }

        return 0;
    }


}
