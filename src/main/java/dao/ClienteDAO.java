package dao;

import entities.Cliente;
import entities.ClienteFacturacion;
import java.util.List;

public interface ClienteDAO {
    void insertClientes(List<Cliente> clientes);
    int countClientes();
    List<ClienteFacturacion> getClientesOrdenadosPorFacturacion();
}
