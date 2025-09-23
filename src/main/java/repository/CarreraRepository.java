package repository;

import dto.CarreraDTO;
import java.util.*;

public interface CarreraRepository {
    void insertarDesdeCSV(String rutaArchivo);
    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    List<CarreraDTO> getCarrerasActivas();
}
