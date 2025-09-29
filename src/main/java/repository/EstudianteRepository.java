package repository;

import com.sun.jdi.event.StepEvent;
import dto.EstudianteDTO;
import java.util.*;

public interface EstudianteRepository {
    void insertarDesdeCSV(String rutaArchivo);

    //a) dar de alta un estudiante
    void addEstudiante(int id,String nombre, String apellido, int edad, String genero, String ciudad, int LU);
    //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple
    List<EstudianteDTO> buscarEstudiantesOrdenadosPor(String atributo);
    //d) recuperar un estudiante, en base a su número de libreta universitaria.
    EstudianteDTO buscarEstudiantePorLU(int LU);
    //e) recuperar todos los estudiantes, en base a su género.
    List<EstudianteDTO> buscarEstudiantesPorGenero(String genero);
    //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
    List<EstudianteDTO> buscarEstudiantesPorCarreraYCiudad(String carrera, String ciudad);

}
