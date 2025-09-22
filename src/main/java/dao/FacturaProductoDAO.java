package dao;

import dto.CarreraDTO;

public interface FacturaProductoDAO {
    CarreraDTO productoMasRecaudado();
    int countFacturasProductos();
}