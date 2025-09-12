package dto;

public class ClienteFacturacionDTO {
    private int idCliente;
    private String nombre;
    private String email;
    private float totalFacturado;

    public ClienteFacturacionDTO(int idCliente, String nombre, String email, float totalFacturado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
        this.totalFacturado = totalFacturado;
    }

    public int getIdCliente() {
        return idCliente;
    }


    public String getNombre() {
        return nombre;
    }


    public String getEmail() {
        return email;
    }


    public float getTotalFacturado() {
        return totalFacturado;
    }


    @Override
    public String toString() {
        return String.format("Cliente ID: %d | %s (%s) | Total facturado: $%.2f",
                idCliente, nombre, email, totalFacturado);
    }
}
