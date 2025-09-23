package dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private int id;
    private String nombre;
    private int duracion;
}