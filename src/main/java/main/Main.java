package main;

import dto.EstudianteDTO;
import repository.*;

public class Main {
    public static void main(String[] args) {
        EstudianteRepositoryImpl er = new EstudianteRepositoryImpl();
        CarreraRepositoryImpl cr = new CarreraRepositoryImpl();
        MatriculaRepositoryImpl mr = new MatriculaRepositoryImpl();

        //cargar datos
        er.insertarDesdeCSV("src/main/resources/estudiantes.csv");
        cr.insertarDesdeCSV("src/main/resources/carreras.csv");
        er.addEstudiante(6397408,"A","B",22,"M","tandil",6397408);
        cr.insertarDesdeCSV("src/main/resources/carreras.csv");

        mr.insertarDesdeCSV("src/main/resources/estudianteCarrera.csv");

        //Prueba consigna A
        er.addEstudiante(6397408,"A","B",22,"Male","tandil",6397408);

        //Prueba consigna C
        //System.out.println("ORDENO ESTUDIANTES POR EDAD: ");
        //for(EstudianteDTO estudiante: er.buscarEstudiantesOrdenadosPor("edad")){
          //  System.out.println(estudiante);
        //}

        //Prueba consigna D
        //System.out.println("MUESTRO ESTUDIANTE POR NÚMERO DE LIBRETA: " +    er.buscarEstudiantePorLU(6397408));

        //Prueba consigna E
        //System.out.println("MUESTRO ESTUDIANTES POR GENERO: ");
       // er.buscarEstudiantesPorGenero("male");

        //Prueba consigna G
        //System.out.println("MUESTRO ESTUDIANTE DE X CARRERA Y CIUDAD: ");
        //er.buscarEstudiantesPorCarreraYCiudad("Abogacia","Idvor");
        cr.getCarrerasDeManeraCronologica();
    }
}