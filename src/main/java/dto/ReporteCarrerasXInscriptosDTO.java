package dto;

import lombok.Getter;

@Getter
public class ReporteCarrerasXInscriptosDTO {

    // Getters
    private String nombreCarrera;
    private Long cantidadInscriptos;

    // Constructor que se usará en la consulta JPQL
    public ReporteCarrerasXInscriptosDTO(String nombreCarrera, Long cantidadInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    /*
    * Método toString con format para mejor legebilidad,
    * %-50s: Carrera ocupa 50 caracteres alineado a la izquierda
    * %4d:   El valor de inscriptos ocupa 4 caracteres alineado a la derecha
    * Ejemplo:
    * TUDAI                                              | Inscriptos:   17
    * Educacion Fisica                                   | Inscriptos:   12
     */
    @Override
    public String toString() {
        return String.format("%-50s | Inscriptos: %4d", nombreCarrera, cantidadInscriptos);
    }
}
