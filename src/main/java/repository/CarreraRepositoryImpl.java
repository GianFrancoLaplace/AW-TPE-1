package repository;

import com.opencsv.CSVReader;

import dto.ReporteCarrerasXInscriptosDTO;
import dto.ReporteCarreraDTO;
import entities.Carrera;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.io.FileReader;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {
    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setId(Integer.parseInt(linea[0]));
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));

                em.merge(carrera);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @Override
    public List<ReporteCarrerasXInscriptosDTO> getCarrerasActivas() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            String jpql = "SELECT new dto.ReporteCarrerasXInscriptosDTO(c.nombre, COUNT(m.estudiante)) " +
                    "FROM Matricula m " +
                    "JOIN m.carrera c " +
                    "GROUP BY c.id, c.nombre " +
                    "ORDER BY COUNT(m.estudiante) DESC";

            Query query = em.createQuery(jpql, ReporteCarrerasXInscriptosDTO.class);
            List<ReporteCarrerasXInscriptosDTO> result = query.getResultList();

            for (ReporteCarrerasXInscriptosDTO reporteCarreras : result) {
                System.out.println(reporteCarreras.toString());
            }

            em.getTransaction().commit();
            em.close();
            return result;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }


    public List<ReporteCarreraDTO> getCarrerasDeManeraCronologica() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            String jpql = "SELECT NEW dto.ReporteCarreraDTO(c.nombre, COUNT(m.id), count(m.graduacion), m.inscripcion) " +
                    "FROM Carrera c " +
                    "JOIN c.matriculas m " +
                    "GROUP BY c.nombre, m.inscripcion " +
                    "ORDER BY c.nombre ASC, m.inscripcion ASC";

            List<ReporteCarreraDTO> reporte = em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();

            System.out.println("--- Reporte Final de Carreras ---");


            for (ReporteCarreraDTO linea : reporte) {
                System.out.println(linea);
            }


            em.close();
            return null;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}

