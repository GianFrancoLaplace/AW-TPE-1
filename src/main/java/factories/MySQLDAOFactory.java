package factories;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;

import java.sql.Connection;

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance;

    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String URL="jdbc:mysql://localhost:3306/";//???
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static Connection conn;


    private static MySQLDAOFactory mySQLDAOFactory;

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
        return null;
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return null;
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return null;
    }

    @Override
    public FacturaProductoDAO getFacturaProductoDAO() {
        return null;
    }
}
