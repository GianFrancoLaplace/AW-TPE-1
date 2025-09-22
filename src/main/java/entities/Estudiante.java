package dto;

public class Estudiante {
    private int idEstudiante;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int LU;

    public Estudiante(){}

    private Estudiante(int idEstudiante, String nombre, String apellido, int edad, String genero, String ciudad, int LU) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudad = ciudad;
        this.LU = LU;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getLU() {
        return LU;
    }
    public void setLU(int LU) {
        this.LU = LU;
    }
}
