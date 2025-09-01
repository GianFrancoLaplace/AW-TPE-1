package entities;

public class ClienteFacturacion {
    private int idCliente;
    private String nombre;
    private String email;
    private float totalFacturado;

    public ClienteFacturacion(int idCliente, String nombre, String email, float totalFacturado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
        this.totalFacturado = totalFacturado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(float totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    @Override
    public String toString() {
        return String.format("Cliente ID: %d | %s (%s) | Total facturado: $%.2f",
                idCliente, nombre, email, totalFacturado);
    }
}
