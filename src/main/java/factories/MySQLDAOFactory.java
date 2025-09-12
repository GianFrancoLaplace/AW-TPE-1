package factories;

import dao.*;
import utils.HelperMySQL;

import java.sql.SQLException;

public class MySQLDAOFactory extends AbstractFactory {
    private static MySQLDAOFactory instance;
    private HelperMySQL helper;

    public MySQLDAOFactory() {
        this.helper = new HelperMySQL();
    }

    public static MySQLDAOFactory getInstance(){
        if(instance==null){
            instance = new MySQLDAOFactory();
        }
        return instance;
    }

    @Override
    public MySQLClienteDAO getClienteDAO() throws SQLException {
        return MySQLClienteDAO.getInstance(helper.getConnection());
    }

    @Override
    public MySQLProductoDAO getProductoDAO() throws SQLException{
        return MySQLProductoDAO.getInstance(helper.getConnection());
    }

    @Override
    public MySQLFacturaDAO getFacturaDAO() throws SQLException{
        return MySQLFacturaDAO.getInstance(helper.getConnection());
    }

    @Override
    public MySQLFacturaProductoDAO getFacturaProductoDAO()throws SQLException {
        return MySQLFacturaProductoDAO.getInstance(helper.getConnection());
    }
}
