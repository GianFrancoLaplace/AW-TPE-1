package utils;

import entities.Cliente;
import entities.Producto;
import entities.Factura;
import entities.FacturaProducto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * Lee clientes desde clientes.csv
     */
    public static List<Cliente> readClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String fileName = "/clientes.csv";

        System.out.println("Leyendo clientes desde CSV...");

        try (InputStream is = CSVReader.class.getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr)) {

            for (CSVRecord row : parser) {
                try {
                    int idCliente = Integer.parseInt(row.get("idCliente").trim());
                    String nombre = row.get("nombre").trim();
                    String email = row.get("email").trim();

                    Cliente cliente = new Cliente(idCliente, nombre, email);
                    clientes.add(cliente);

                } catch (NumberFormatException e) {
                    System.err.println("Error parseando número en fila " + parser.getCurrentLineNumber());
                } catch (IllegalArgumentException e) {
                    System.err.println("Columna faltante en fila " + parser.getCurrentLineNumber());
                }
            }

            System.out.println(clientes.size() + " clientes leídos correctamente");

        } catch (IOException e) {
            System.err.println("Error leyendo " + fileName + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Lee productos desde productos.csv
     */
    public static List<Producto> readProductos() {
        List<Producto> productos = new ArrayList<>();
        String fileName = "/productos.csv";

        System.out.println("Leyendo productos desde CSV...");

        try (InputStream is = CSVReader.class.getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr)) {

            for (CSVRecord row : parser) {
                try {
                    int idProducto = Integer.parseInt(row.get("idProducto").trim());
                    String nombre = row.get("nombre").trim();
                    float valor = Float.parseFloat(row.get("valor").trim());

                    Producto producto = new Producto(idProducto, nombre, valor);
                    productos.add(producto);

                } catch (NumberFormatException e) {
                    System.err.println("Error parseando número en fila " + parser.getCurrentLineNumber());
                }
            }

            System.out.println(productos.size() + " productos leídos correctamente");

        } catch (IOException e) {
            System.err.println("Error leyendo " + fileName + ": " + e.getMessage());
        }

        return productos;
    }

    /**
     * Lee facturas desde facturas.csv
     */
    public static List<Factura> readFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String fileName = "/facturas.csv";

        System.out.println("Leyendo facturas desde CSV...");

        try (InputStream is = CSVReader.class.getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr)) {

            for (CSVRecord row : parser) {
                try {
                    int idFactura = Integer.parseInt(row.get("idFactura").trim());
                    int idCliente = Integer.parseInt(row.get("idCliente").trim());

                    Factura factura = new Factura(idFactura, idCliente);
                    facturas.add(factura);

                } catch (NumberFormatException e) {
                    System.err.println("Error parseando número en fila " + parser.getCurrentLineNumber());
                }
            }

            System.out.println(facturas.size() + " facturas leídas correctamente");

        } catch (IOException e) {
            System.err.println("Error leyendo " + fileName + ": " + e.getMessage());
        }

        return facturas;
    }

    /**
     * Lee relaciones factura-producto desde facturasproductos.csv
     */
    public static List<FacturaProducto> readFacturasProductos() {
        List<FacturaProducto> facturasProductos = new ArrayList<>();
        String fileName = "/facturasproductos.csv";

        System.out.println("Leyendo facturas-productos desde CSV...");

        try (InputStream is = CSVReader.class.getResourceAsStream(fileName);
             InputStreamReader isr = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr)) {

            for (CSVRecord row : parser) {
                try {
                    int idFactura = Integer.parseInt(row.get("idFactura").trim());
                    int idProducto = Integer.parseInt(row.get("idProducto").trim());
                    int cantidad = Integer.parseInt(row.get("cantidad").trim());

                    FacturaProducto fp = new FacturaProducto(idFactura, idProducto, cantidad);
                    facturasProductos.add(fp);

                } catch (NumberFormatException e) {
                    System.err.println("Error parseando número en fila " + parser.getCurrentLineNumber());
                }
            }

            System.out.println(facturasProductos.size() + " relaciones factura-producto leídas correctamente");

        } catch (IOException e) {
            System.err.println("Error leyendo " + fileName + ": " + e.getMessage());
        }

        return facturasProductos;
    }
}