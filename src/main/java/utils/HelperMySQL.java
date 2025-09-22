package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

public class HelperMySQL {
    private Connection conn = null;

    public Connection getConnection() throws SQLException {
        return this.conn;
    }

    public HelperMySQL() {
        String url = "jdbc:mysql://localhost:3306/entregable2";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(url,"root","");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEstudiante() throws IOException {
        String path = "src/main/resources/estudiantes.csv";
        System.out.println("Insertando estudiantes desde: " + path);

        int contador = 0;
        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {

            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO estudiante (id_estudiante, nombre, apellido, edad, genero, ciudad, LU) VALUES (?,?,?,?,?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setString(2, row.get(1));
                    ps.setString(3, row.get(2));
                    ps.setInt(4, Integer.parseInt(row.get(3)));
                    ps.setString(5, row.get(4));
                    ps.setString(6, row.get(5));
                    ps.setInt(7, Integer.parseInt(row.get(6)));
                    ps.executeUpdate();
                    contador++;
                }
            }
            this.conn.commit();
            System.out.println("Estudiantes insertados: " + contador);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void insertCarrera() throws IOException {
        String path = "src/main/resources/carreras.csv";
        System.out.println("Insertando carreras desde: " + path);

        int contador = 0;
        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {
            for (CSVRecord row : csvParser) {
                String query = "INSERT INTO carrera (id_carrera, carrera, duracion) VALUES (?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(row.get(0)));
                    ps.setString(2, row.get(1));
                    ps.setInt(3, Integer.parseInt(row.get(2)));
                    ps.executeUpdate();
                    contador++;
                }
            }
            this.conn.commit();
            System.out.println("Carreras insertadas: " + contador);
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void insertEstudiateCarrera() throws IOException {
        String path = "src/main/resources/estudianteCarrera.csv";
        System.out.println("Insertando estudiante-carrera desde: " + path);
        int contador = 0;

        try (Reader in = new FileReader(path);
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(in)) {
            for (CSVRecord row : csvParser) {
                try {
                    String query = "INSERT INTO estudianteCarrera (id, id_estudiante, id_carrera, inscripcion, graduacion, antiguedad) VALUES (?,?,?,?,?,?)";
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setInt(1, Integer.parseInt(row.get(0)));
                        ps.setInt(2, Integer.parseInt(row.get(1)));
                        ps.setInt(3, Integer.parseInt(row.get(2)));
                        ps.setInt(4, Integer.parseInt(row.get(3)));
                        ps.setInt(5, Integer.parseInt(row.get(4)));
                        ps.setInt(6, Integer.parseInt(row.get(5)));
                        ps.executeUpdate();
                        contador++;
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            this.conn.commit();
            System.out.println("Carreras insertadas: " + contador);
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void createSchema() {
        System.out.println("Creando esquema de base de datos...");

        try {
            Statement stmt = conn.createStatement();

            String createCarrera = "CREATE TABLE carrera (" +
                    "id_carrera INTEGER NOT NULL PRIMARY KEY," +
                    "carrera VARCHAR(500)," +
                    "duracion INTEGER" +
                    ")";

            String createEstudiante = "CREATE TABLE estudiante (" +
                    "id_estudiante INTEGER NOT NULL PRIMARY KEY, " +
                    "nombre VARCHAR(500), " +
                    "apellido VARCHAR(150)," +
                    "edad INTEGER," +
                    "genero VARCHAR(150)," +
                    "ciudad VARCHAR(150)," +
                    "LU INTEGER" +
                    ")";

            String createEstudianteCarrera = "CREATE TABLE estudianteCarrera (" +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "id_estudiante INTEGER, " +
                    "id_carrera INTEGER, " +
                    "inscripcion INTEGER, " +
                    "graduacion INTEGER, " +
                    "antiguedad INTEGER, " +
                    "FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_estudiante)," +
                    "FOREIGN KEY (id_carrera) REFERENCES carrera(id_carrera)" +
                    ")";

            stmt.executeUpdate(createCarrera);
            System.out.println("Tabla Carrera creada");

            stmt.executeUpdate(createEstudiante);
            System.out.println("Tabla Estudiante creada");

            stmt.executeUpdate(createEstudianteCarrera);
            System.out.println("Tabla CarreraEstudiante creada");

            this.conn.commit();
            System.out.println("Esquema creado exitosamente!");

        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Las tablas ya existen. Usa dropSchema() primero si quieres recrearlas");
            } else {
                System.out.println("Error creando esquema: " + e.getMessage());
            }
        }
    }

    public void dropSchema() {
        System.out.println("Eliminando esquema...");

        try {
            Statement stmt = conn.createStatement();
            // Orden importante: eliminar dependencias primero
            stmt.executeUpdate("DROP TABLE IF EXISTS estudianteCarrera");
            stmt.executeUpdate("DROP TABLE IF EXISTS estudiante");
            stmt.executeUpdate("DROP TABLE IF EXISTS carrera");
            this.conn.commit();
            System.out.println("Esquema eliminado");

        } catch (SQLException e) {
            System.out.println("Error eliminando esquema: " + e.getMessage());
        }
    }
}