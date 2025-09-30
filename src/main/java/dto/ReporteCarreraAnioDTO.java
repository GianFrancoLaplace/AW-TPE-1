package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ReporteCarreraAnioDTO {
    private int idCarrera;
    private int id_estudiante;
    private String nombre;
    private int inscripcion;
    private Integer graduacion;
    private int antiguedad;


}
