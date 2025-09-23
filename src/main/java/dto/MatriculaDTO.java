package dto;

import entities.Carrera;
import entities.Estudiante;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatriculaDTO {
    private int id;
    private Estudiante estudiante;
    private Carrera carrera;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
}
