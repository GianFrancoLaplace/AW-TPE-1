package factories;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;

import java.sql.Connection;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;


    public abstract Connection getConnection();


    public abstract ClienteDAO getClienteDAO();

    public abstract ProductoDAO getProductoDAO();

    public abstract FacturaDAO getFacturaDAO();

    public abstract FacturaProductoDAO getFacturaProductoDAO();
}
