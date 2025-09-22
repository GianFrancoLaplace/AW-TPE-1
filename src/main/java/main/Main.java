package main;

import utils.HelperMySQL;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        HelperMySQL helperMySQL = new HelperMySQL();

        helperMySQL.dropSchema();

        helperMySQL.createSchema();
        helperMySQL.insertCarrera();
        helperMySQL.insertEstudiante();
        helperMySQL.insertEstudiateCarrera();
    }
}