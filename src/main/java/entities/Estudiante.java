package entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estudiante")
public class Estudiante {

    @Id
    private int id;

    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int documento;
    private int nroLibreta;
    //AK
    @Column(name = "LU")
    private int LU;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", id=" + id +
                ", genero='" + genero + '\'' +
                '}';
    }
}

