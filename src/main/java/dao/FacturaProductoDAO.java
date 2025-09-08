package dao;

import entities.FacturaProducto;
import java.util.List;

public interface FacturaProductoDAO {
    void insertFacturasProductos(List<FacturaProducto> facturasProductos);
    int countFacturasProductos();
}