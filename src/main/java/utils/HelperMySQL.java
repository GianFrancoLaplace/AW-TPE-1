package utils;

import entities.FacturaProducto;
import entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.List;

public class HelperMySQL {
    private Connection conn = null;

    public HelperMySQL() {
        String url = "jdbc:mysql://localhost:3306/Entregable1";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(url,"root","");
            conn.setAutoCommit(false);//transacciones manuales
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return this.conn;
    }

    public void insertProductos() throws IOException {
        String path = "src/main/resources/productos.csv";

        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {

            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setString(2, row.get(1));
                    ps.setFloat(3, Float.parseFloat(row.get(2)));
                    ps.executeUpdate();
                }
            }
            this.conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFacturasProductos() throws IOException {
        String path = "src/main/resources/facturas-productos.csv";

        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {

            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setInt(2, Integer.parseInt(row.get(1)));
                    ps.setInt(3, Integer.parseInt(row.get(2)));
                    ps.executeUpdate();
                }
            }
            this.conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCliente() throws IOException {
        String path = "src/main/resources/clientes.csv";

        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {

            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setString(2, row.get(1));
                    ps.setString(3, row.get(2));
                    ps.executeUpdate();
                }
            }
            this.conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFactura() throws IOException {
        String path = "src/main/resources/facturas.csv";

        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {

            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO factura (idFactura, idCliente) VALUES (?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setInt(2, Integer.parseInt(row.get(1)));
                    ps.executeUpdate();
                }
            }
            this.conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSchema() {
        System.out.println("Creando esquema de base de datos...");

        try {
            Statement stmt = conn.createStatement();

            String createCliente = "CREATE TABLE cliente (" +
                    "idCliente INTEGER NOT NULL PRIMARY KEY, " +
                    "nombre VARCHAR(500), " +
                    "email VARCHAR(150)" +
                    ")";

            String createProducto = "CREATE TABLE producto (" +
                    "idProducto INTEGER NOT NULL PRIMARY KEY, " +
                    "nombre VARCHAR(45), " +
                    "valor FLOAT" +
                    ")";

            String createFactura = "CREATE TABLE factura (" +
                    "idFactura INTEGER NOT NULL PRIMARY KEY, " +
                    "idCliente INTEGER, " +
                    "FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)" +
                    ")";

            String createFacturaProducto = "CREATE TABLE factura_producto (" +
                    "idFactura INTEGER, " +
                    "idProducto INTEGER, " +
                    "cantidad INTEGER, " +
                    "PRIMARY KEY (idFactura, idProducto), " +
                    "FOREIGN KEY (idFactura) REFERENCES factura(idFactura), " +
                    "FOREIGN KEY (idProducto) REFERENCES producto(idProducto)" +
                    ")";

            stmt.executeUpdate(createCliente);
            System.out.println("Tabla Cliente creada");

            stmt.executeUpdate(createProducto);
            System.out.println(" Tabla Producto creada");

            stmt.executeUpdate(createFactura);
            System.out.println("Tabla Factura creada");

            stmt.executeUpdate(createFacturaProducto);
            System.out.println("Tabla Factura_Producto creada");

            this.conn.commit();
            System.out.println("Esquema creado exitosamente!");

        } catch (SQLException e) {
            System.out.println("Error creando esquema: " + e.getMessage());

            // Diagnóstico específico
            if (e.getMessage().contains("already exists")) {
                System.out.println("Las tablas ya existen. Usa dropSchema() primero si quieres recrearlas");
            }
        }
    }

    public void dropSchema() {
        System.out.println("Eliminando esquema...");

        try {
            Statement stmt = conn.createStatement();
            // Orden importante: eliminar dependencias primero
            stmt.executeUpdate("DROP TABLE IF EXISTS Factura_Producto");
            stmt.executeUpdate("DROP TABLE IF EXISTS Factura");
            stmt.executeUpdate("DROP TABLE IF EXISTS Producto");
            stmt.executeUpdate("DROP TABLE IF EXISTS Cliente");
            this.conn.commit();
            System.out.println("Esquema eliminado");

        } catch (SQLException e) {
            System.out.println("Error eliminando esquema: " + e.getMessage());
        }
    }
}

