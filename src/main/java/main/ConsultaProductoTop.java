package main;

import dao.ProductoDAO;
import entities.ProductoRecaudacion;
import utils.DatabaseConnection;

public class ConsultaProductoTop {

    public static void main(String[] args) {
        System.out.println("CONSULTA: PRODUCTO QUE MAS RECAUDO");
        System.out.println("===================================");

        DatabaseConnection.testConnection();
        System.out.println();

        ProductoRecaudacion productoTop = ProductoDAO.getProductoMasRecaudo();

        if (productoTop != null) {
            System.out.println("RESULTADO:");
            System.out.println("-----------");
            System.out.println(productoTop);
        } else {
            System.out.println("No se encontraron productos con ventas registradas.");
        }

        System.out.println();
        System.out.println("Consulta completada.");
    }
}