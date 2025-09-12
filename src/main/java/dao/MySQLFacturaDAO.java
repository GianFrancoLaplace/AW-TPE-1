package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLFacturaDAO implements FacturaDAO {
    private Connection connection;
    private static MySQLFacturaDAO instance;

    public MySQLFacturaDAO(Connection connection) {
        this.connection = connection;
    }

    public static MySQLFacturaDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new MySQLFacturaDAO(connection);
        }
        return instance;
    }

    public int countFacturas() {
        String sql = "SELECT COUNT(*) as total FROM Factura";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error contando facturas: " + e.getMessage());
        }

        return 0;
    }
}
