package dao;

import dto.ProductoRecaudacionDTO;
import entities.FacturaProducto;
import java.util.List;

public interface FacturaProductoDAO {
    ProductoRecaudacionDTO productoMasRecaudado();
    int countFacturasProductos();
}