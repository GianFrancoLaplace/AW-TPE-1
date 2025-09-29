package repository;

import com.opencsv.CSVReader;
import dto.EstudianteDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Matricula;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManager em;

    public EstudianteRepositoryImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante = new Estudiante();
                estudiante.setId(Integer.parseInt(linea[0]));
                estudiante.setNombre(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudad(linea[5]);
                estudiante.setLU(Integer.parseInt(linea[6]));

                em.merge(estudiante);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    //a) dar de alta un estudiante
    @Override
    public void addEstudiante(int id, String nombre, String apellido, int edad, String genero, String ciudad, int LU) {
        try {
            em.getTransaction().begin();

            Estudiante estudiante = new Estudiante();
            estudiante.setId(id); //DNI
            estudiante.setNombre(nombre);
            estudiante.setApellido(apellido);
            estudiante.setEdad(edad);
            estudiante.setGenero(genero);
            estudiante.setCiudad(ciudad);
            estudiante.setLU(LU);

            em.persist(estudiante);

            em.getTransaction().commit();
            System.out.println("estudiante insertado: " + id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    //c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple
    @Override
    public List<EstudianteDTO> buscarEstudiantesOrdenadosPor(String genero) {
        try {
            return (List<EstudianteDTO>) em.createQuery(
                    "SELECT new dto.EstudianteDTO(e.id, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.documento, e.LU)" +
                            "FROM Estudiante e " +
                            "WHERE e.genero = :genero",
                    EstudianteDTO.class
            ).setParameter("genero", genero).getResultList();

        } catch (Exception e) {
            System.out.println("Error al buscar estudiantes: " + e.getMessage());
            return new ArrayList<>(); // Retorna lista vacía en lugar de null
        }
    }

    //d) recuperar un estudiante, en base a su número de libreta universitaria.
    @Override
    public EstudianteDTO buscarEstudiantePorLU(int LU) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.LU = :LU";

        try {
            Estudiante estudiante = em.createQuery(jpql, Estudiante.class)
                    .setParameter("LU", LU)
                    .getSingleResult();

            return new EstudianteDTO(estudiante);

        } catch (Error e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    //e) recuperar todos los estudiantes, en base a su género.
    @Override
    public List<EstudianteDTO> buscarEstudiantesPorGenero(String genero) {
//        try {
//            return (List<EstudianteDTO>) em.createQuery(
//                    "SELECT new dto.EstudianteDTO(e.id, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.documento, e.LU) FROM Estudiante e WHERE e.genero = :genero",
//                    EstudianteDTO.class
//            );
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//             return null;
//        }
        return null;

    }

    //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
    @Override
    public List<EstudianteDTO> buscarEstudiantesPorCarreraYCiudad(String carrera, String ciudad) {
        try {
            return (List<EstudianteDTO>) em.createQuery(
                    "SELECT new dto.EstudianteDTO(e.id, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.documento, e.LU)" +
                            "FROM Matricula m JOIN Estudiante e " +
                            "WHERE e.ciudad = :ciudad AND m.carrera.nombre = :carrera",
                    EstudianteDTO.class
            ).setParameter("ciudad", carrera).setParameter("ciudad", ciudad).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
