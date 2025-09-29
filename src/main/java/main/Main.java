package main;

import repository.*;

public class Main {
    public static void main(String[] args) {
        EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
        CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
        MatriculaRepositoryImpl mr = new MatriculaRepositoryImpl();

        //cargar datos
        er.insertarDesdeCSV("src/main/resources/estudiantes.csv");
        cr.insertarDesdeCSV("src/main/resources/carreras.csv");
//        er.addEstudiante(6397408,"A","B",22,"M","tandil",6397408);
        mr.insertarDesdeCSV("src/main/resources/estudianteCarrera.csv");
        er.buscarEstudiantesPorGenero("male");
//        er.buscarEstudiantesOrdenadosPor("edad");
//        er.buscarEstudiantePorLU(1);
    }
}