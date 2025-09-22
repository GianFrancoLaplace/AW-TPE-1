package dao;

import dto.EstudianteDTO;

import java.sql.Connection;

public class MySQLEstudianteDAO implements EstudianteDAO {
    private Connection connection;
    private static MySQLEstudianteDAO instance;

    public MySQLEstudianteDAO(Connection connection) {
        this.connection = connection;
    }

    public static MySQLEstudianteDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new MySQLEstudianteDAO(connection);
        }
        return instance;
    }

    @Override
    public EstudianteDTO getEstudiante(int idEstudiante) {
        return new EstudianteDTO();
    }
}
