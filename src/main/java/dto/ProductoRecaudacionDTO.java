package dto;

public class ProductoRecaudacionDTO {
    private int idProducto;
    private String nombre;
    private float recaudacionTotal;

    public ProductoRecaudacionDTO(int idProducto, String nombre, float recaudacionTotal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.recaudacionTotal = recaudacionTotal;
    }

    public int getIdProducto() {
        return idProducto;
    }


    public String getNombre() {
        return nombre;
    }


    public float getRecaudacionTotal() {
        return recaudacionTotal;
    }


    @Override
    public String toString() {
        return String.format("Producto ID: %d\nNombre: %s\nRecaudación total: $%.2f",
                idProducto, nombre,recaudacionTotal);
    }
}