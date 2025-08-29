package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/arquitecturas";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "";

    public static Connection getMySQLConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de MySQL no encontrado");
            throw new SQLException("MySQL driver no encontrado", e);
        }
    }

    public static void testConnection() {
        System.out.println("Probando conexión a MySQL...");
        System.out.println("   URL: " + MYSQL_URL);
        System.out.println("   Usuario: " + MYSQL_USER);

        try (Connection conn = getMySQLConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión exitosa a MySQL");
                printConnectionInfo(conn);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a MySQL");
            System.out.println("   Mensaje: " + e.getMessage());

            if (e.getMessage().contains("Unknown database")) {
                System.out.println("La base de datos 'arquitecturas' no existe. Ejecute: CREATE DATABASE arquitecturas;");
            } else if (e.getMessage().contains("Access denied")) {
                System.out.println("Credenciales incorrectas. Verifique usuario y contraseña.");
            } else if (e.getMessage().contains("Connection refused")) {
                System.out.println("No se puede conectar al servidor MySQL. Verifique que esté ejecutándose en el puerto 3306.");
            }
        }
    }

    private static void printConnectionInfo(Connection conn) throws SQLException {
        System.out.println("   - Driver: " + conn.getMetaData().getDriverName());
        System.out.println("   - Versión: " + conn.getMetaData().getDriverVersion());
        System.out.println("   - URL: " + conn.getMetaData().getURL());
    }
}
