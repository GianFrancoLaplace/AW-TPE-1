package repository;

import dto.*;

import java.util.*;

public interface MatriculaRepository {
    void insertarDesdeCSV(String rutaArchivo);
    //b) matricular un estudiante en una carrera
    void addMatricula(EstudianteDTO estudiante, CarreraDTO carrera,int inscripcion,int graduacion, int antiguedad);
}
