package dao;

import entities.Producto;
import entities.ProductoRecaudacion;
import java.util.List;


public interface ProductoDAO {
    void insertProductos(List<Producto> productos);
    int countProductos();
    ProductoRecaudacion productoMasRecaudado();
}