package main;

import dto.ClienteFacturacionDTO;
import dto.ProductoRecaudacionDTO;
import factories.AbstractFactory;
import utils.HelperMySQL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        HelperMySQL helperMySQL = new HelperMySQL();

        helperMySQL.dropSchema();

        helperMySQL.createSchema();
        helperMySQL.insertCliente();
        helperMySQL.insertFactura();
        helperMySQL.insertProductos();
        helperMySQL.insertFacturasProductos();

        //consulta clientes por facturacion
        List<ClienteFacturacionDTO> aux = AbstractFactory.getInstance().getClienteDAO().getClientesOrdenadosPorFacturacion();
        System.out.println("clientes ordenados por facturacion");
        System.out.println("-----------");
        for (ClienteFacturacionDTO cliente : aux) {
            System.out.println(cliente);
        }

        //consulta producto mas recaudado
        ProductoRecaudacionDTO producto= AbstractFactory.getInstance().getFacturaProductoDAO().productoMasRecaudado();
        System.out.println("producto con mayor recaudacion: " + producto);
    }
}