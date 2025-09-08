package main;

import dao.ClienteDAO;
import entities.ClienteFacturacion;
import factories.MySQLDAOFactory;
import utils.DatabaseConnection;

import java.util.List;

public class ConsultaClienteFacturacion {
    public static void main(String[] args) {
        System.out.println("CONSULTA: CLIENTES ORDENADOS POR FACTURACION");
        System.out.println("============================================");

        DatabaseConnection.testConnection();
        System.out.println();

        MySQLDAOFactory factory = MySQLDAOFactory.getInstance();
        ClienteDAO clienteDAO = factory.getClienteDAO();
        List<ClienteFacturacion> clientes = clienteDAO.getClientesOrdenadosPorFacturacion();

        if (clientes.isEmpty()) {
            System.out.println("No se encontraron clientes con facturas registradas.");
            return;
        }

        System.out.println("RANKING DE CLIENTES POR FACTURACION:");
        System.out.println("-------------------------------------");

        int posicion = 1;
        for (ClienteFacturacion cliente : clientes) {
            System.out.printf("%2d. %s%n", posicion, cliente);
            posicion++;
        }

        System.out.println();
        System.out.printf("Total de clientes con facturas: %d%n", clientes.size());
        System.out.println("Consulta completada.");
    }
}