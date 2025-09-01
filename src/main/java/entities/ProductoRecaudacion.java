package entities;

public class ProductoRecaudacion {
    private int idProducto;
    private String nombre;
    private float valor;
    private float recaudacionTotal;

    public ProductoRecaudacion(int idProducto, String nombre, float valor, float recaudacionTotal) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        this.recaudacionTotal = recaudacionTotal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(float recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }

    @Override
    public String toString() {
        return String.format("Producto ID: %d\nNombre: %s\nValor unitario: $%.2f\nRecaudación total: $%.2f",
                idProducto, nombre, valor, recaudacionTotal);
    }
}