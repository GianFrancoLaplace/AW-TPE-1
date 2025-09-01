package main;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import entities.Cliente;
import entities.Producto;
import entities.Factura;
import entities.FacturaProducto;
import utils.CSVReader;
import utils.DatabaseConnection;

import java.util.List;

public class DataLoader {

    public static void main(String[] args) {
        System.out.println("INICIANDO CARGA MASIVA DE DATOS");
        System.out.println("==================================\n");

        // Paso 1: Verificar conexión
        DatabaseConnection.testConnection();
        System.out.println();

        // Paso 2: Mostrar estado inicial
        showDatabaseStatus("ESTADO INICIAL");

        // Paso 3: Cargar datos en orden correcto (integridad referencial)
        loadClientes();
        loadProductos();
        loadFacturas();
        loadFacturasProductos();

        // Paso 4: Mostrar estado final
        showDatabaseStatus("ESTADO FINAL");

        System.out.println("PROCESO DE CARGA COMPLETADO!");
        System.out.println("===============================");
    }

    private static void loadClientes() {
        System.out.println("=== CARGANDO CLIENTES ===");

        List<Cliente> clientes = CSVReader.readClientes();

        if (!clientes.isEmpty()) {
            ClienteDAO.insertClientes(clientes);
            System.out.println("Total clientes en base: " + ClienteDAO.countClientes());
        } else {
            System.out.println("No se pudieron leer clientes del CSV");
        }

        System.out.println();
    }

    private static void loadProductos() {
        System.out.println("=== CARGANDO PRODUCTOS ===");

        List<Producto> productos = CSVReader.readProductos();

        if (!productos.isEmpty()) {
            ProductoDAO.insertProductos(productos);
            System.out.println("Total productos en base: " + ProductoDAO.countProductos());
        } else {
            System.out.println("No se pudieron leer productos del CSV");
        }

        System.out.println();
    }

    private static void loadFacturas() {
        System.out.println("=== CARGANDO FACTURAS ===");

        List<Factura> facturas = CSVReader.readFacturas();

        if (!facturas.isEmpty()) {
            FacturaDAO.insertFacturas(facturas);
            System.out.println("Total facturas en base: " + FacturaDAO.countFacturas());
        } else {
            System.out.println("No se pudieron leer facturas del CSV");
        }

        System.out.println();
    }

    private static void loadFacturasProductos() {
        System.out.println("=== CARGANDO RELACIONES FACTURA-PRODUCTO ===");

        List<FacturaProducto> facturasProductos = CSVReader.readFacturasProductos();

        if (!facturasProductos.isEmpty()) {
            FacturaProductoDAO.insertFacturasProductos(facturasProductos);
            System.out.println("Total relaciones en base: " + FacturaProductoDAO.countFacturasProductos());
        } else {
            System.out.println("No se pudieron leer facturas-productos del CSV");
        }

        System.out.println();
    }

    private static void showDatabaseStatus(String titulo) {
        System.out.println("=== " + titulo + " ===");
        System.out.println("   Clientes: " + ClienteDAO.countClientes());
        System.out.println("   Productos: " + ProductoDAO.countProductos());
        System.out.println("   Facturas: " + FacturaDAO.countFacturas());
        System.out.println("   Relaciones: " + FacturaProductoDAO.countFacturasProductos());
        System.out.println();
    }
}