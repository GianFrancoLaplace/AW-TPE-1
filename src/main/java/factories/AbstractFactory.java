package factories;

import dao.EstudianteDAO;

import java.sql.SQLException;


public abstract class AbstractFactory {
    public static AbstractFactory getInstance() {
        return MySQLDAOFactory.getInstance();
    }

    public abstract EstudianteDAO getEstudiante() throws SQLException;
}
