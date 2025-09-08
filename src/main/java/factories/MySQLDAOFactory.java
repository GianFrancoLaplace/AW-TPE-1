package factories;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;

import java.sql.Connection;

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance;

    private static Connection conn;

    public static MySQLDAOFactory getInstance(){
        if(instance==null){
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    @Override
    public Connection getConnection()  {
        return conn;
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new MySQLClienteDAO(conn);
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return new MySQLProductoDAO(conn);
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new MySQLFacturaDAO(conn);
    }

    @Override
    public FacturaProductoDAO getFacturaProductoDAO() {
        return new MySQLFacturaProductoDAO(conn);
    }
}
