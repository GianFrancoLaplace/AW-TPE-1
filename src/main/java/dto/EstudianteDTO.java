package dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EstudianteDTO {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private String documento;
    private String nroLibreta;
}
