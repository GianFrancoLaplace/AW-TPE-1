// dto/ReporteCarreraDTO.java
package dto;

public class ReporteCarreraDTO {
    private String nombreCarrera;
    private long inscriptos;
    private long egresados;
    private int inscripcion; // Usamos int para que coincida con la entidad

    // Constructor que usará JPQL
    public ReporteCarreraDTO(String nombreCarrera, long inscriptos, Long egresados, int inscripcion) {
        this.nombreCarrera = nombreCarrera;
        this.inscriptos = inscriptos;
        this.egresados = (egresados != null) ? egresados : 0L;
        this.inscripcion = inscripcion;
    }

    @Override
    public String toString() {
        return "Carrera: " + nombreCarrera +
                ", Año: " + inscripcion +
                ", Inscriptos: " + inscriptos +
                ", Egresados: " + egresados;
    }
}