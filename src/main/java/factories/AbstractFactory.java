package factories;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;

import java.sql.SQLException;


public abstract class AbstractFactory {
    public static AbstractFactory getInstance() {
        return MySQLDAOFactory.getInstance();
    }

    public abstract ClienteDAO getClienteDAO() throws SQLException;

    public abstract ProductoDAO getProductoDAO() throws SQLException;

    public abstract FacturaDAO getFacturaDAO() throws SQLException;

    public abstract FacturaProductoDAO getFacturaProductoDAO() throws SQLException;
}
