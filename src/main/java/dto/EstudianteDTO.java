package dto;

import entities.Estudiante;
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
    private int documento;
    private int nroLibreta;

    public EstudianteDTO(Estudiante e) {
        this.id = e.getId();  // o el nombre de tu PK
        this.nombre = e.getNombre();
        this.apellido = e.getApellido();
        this.edad = e.getEdad();
        this.genero = e.getGenero();
        this.ciudad = e.getCiudad();
        this.documento = e.getDocumento();
        this.nroLibreta = e.getNroLibreta();
    }
}
