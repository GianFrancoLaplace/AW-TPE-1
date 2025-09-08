package main;

import utils.DatabaseConnection;
import utils.SchemaCreator;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.testConnection();
        System.out.println();

        // Elimina esquema de DB, descomentar si ya fue creada
         SchemaCreator.dropSchema();

        SchemaCreator.createSchema();
        System.out.println();

        System.out.println("Configuración inicial completada!");
    }
}