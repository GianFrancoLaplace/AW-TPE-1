package repository;

import com.opencsv.CSVReader;
import dto.CarreraDTO;
import dto.EstudianteDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Matricula;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;

import java.io.FileReader;

public class MatriculaRepositoryImpl implements MatriculaRepository {
    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                int estudianteId = Integer.parseInt(linea[1]);
                Estudiante estudiante = em.find(Estudiante.class, estudianteId);
                int carreraid = Integer.parseInt(linea[2]);
                Carrera carrera = em.find(Carrera.class, carreraid);

                if (estudiante == null) {
                    System.out.println(linea[0]+" estudiante no encontrado: " + estudianteId);
                    continue;
                }
                if (carrera == null) {
                    System.out.println(" carrera no encontrada: " + carreraid);
                    continue;
                }

                Matricula matricula = new Matricula();
                matricula.setId(Integer.parseInt(linea[0]));
                matricula.setEstudiante(estudiante);
                matricula.setCarrera(carrera);
                matricula.setInscripcion(Integer.parseInt(linea[3]));
                matricula.setGraduacion(Integer.parseInt(linea[4]));
                matricula.setAntiguedad(Integer.parseInt(linea[5]));

                em.persist(matricula);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //b) matricular un estudiante en una carrera
    @Override
    public void addMatricula(EstudianteDTO estudiante, CarreraDTO carrera, int inscripcion, int graduacion, int antiguedad) {

    }

}
