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
    public EstudianteDAO getEstudiante() throws SQLException {
        return MySQLEstudianteDAO.getInstance(helper.getConnection());
    }
}
