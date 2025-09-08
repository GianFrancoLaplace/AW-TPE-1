package dao;

import entities.Factura;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {
    void insertFacturas(List<Factura> facturas);
    int countFacturas();
}