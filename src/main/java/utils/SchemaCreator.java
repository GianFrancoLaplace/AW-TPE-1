package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaCreator {

    public static void createSchema() {
        System.out.println("🏗️ Creando esquema de base de datos...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             Statement stmt = conn.createStatement()) {

            String createCliente = "CREATE TABLE Cliente (" +
                    "idCliente INTEGER NOT NULL PRIMARY KEY, " +
                    "nombre VARCHAR(500), " +
                    "email VARCHAR(150)" +
                    ")";

            String createProducto = "CREATE TABLE Producto (" +
                    "idProducto INTEGER NOT NULL PRIMARY KEY, " +
                    "nombre VARCHAR(45), " +
                    "valor FLOAT" +
                    ")";

            String createFactura = "CREATE TABLE Factura (" +
                    "idFactura INTEGER NOT NULL PRIMARY KEY, " +
                    "idCliente INTEGER, " +
                    "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)" +
                    ")";

            String createFacturaProducto = "CREATE TABLE Factura_Producto (" +
                    "idFactura INTEGER, " +
                    "idProducto INTEGER, " +
                    "cantidad INTEGER, " +
                    "PRIMARY KEY (idFactura, idProducto), " +
                    "FOREIGN KEY (idFactura) REFERENCES Factura(idFactura), " +
                    "FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)" +
                    ")";

            stmt.executeUpdate(createCliente);
            System.out.println("Tabla Cliente creada");

            stmt.executeUpdate(createProducto);
            System.out.println(" Tabla Producto creada");

            stmt.executeUpdate(createFactura);
            System.out.println("Tabla Factura creada");

            stmt.executeUpdate(createFacturaProducto);
            System.out.println("Tabla Factura_Producto creada");

            System.out.println("Esquema creado exitosamente!");

        } catch (SQLException e) {
            System.out.println("Error creando esquema: " + e.getMessage());

            // Diagnóstico específico
            if (e.getMessage().contains("already exists")) {
                System.out.println("Las tablas ya existen. Usa dropSchema() primero si quieres recrearlas");
            }
        }
    }

    public static void dropSchema() {
        System.out.println("Eliminando esquema...");

        try (Connection conn = DatabaseConnection.getMySQLConnection();
             Statement stmt = conn.createStatement()) {

            // Orden importante: eliminar dependencias primero
            stmt.executeUpdate("DROP TABLE IF EXISTS Factura_Producto");
            stmt.executeUpdate("DROP TABLE IF EXISTS Factura");
            stmt.executeUpdate("DROP TABLE IF EXISTS Producto");
            stmt.executeUpdate("DROP TABLE IF EXISTS Cliente");

            System.out.println("Esquema eliminado");

        } catch (SQLException e) {
            System.out.println("Error eliminando esquema: " + e.getMessage());
        }
    }
}