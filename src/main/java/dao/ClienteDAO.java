package dao;

import dto.ClienteFacturacionDTO;
import java.util.List;

public interface ClienteDAO {
    int countClientes();
    List<ClienteFacturacionDTO> getClientesOrdenadosPorFacturacion();
}
