package repository;

import com.opencsv.CSVReader;

import dto.ReporteCarreraAnioDTO;
import dto.ReporteCarrerasXInscriptosDTO;
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


    public List<ReporteCarreraAnioDTO> getCarrerasDeManeraCronologica() {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        try {
            String queryInscriptos = "SELECT e " +
                    "COUNT(m.inscripciones) " +
                    "COUNT(m.egresados) " +
                    "FROM Carrera c " +
                    "LEFT JOIN Matricula m " +
                    "ON c.id = m.id " +
                    "ORDER BY c.nombre ASC, m.inscripcion ASC ";


            Query query = em.createNativeQuery(queryInscriptos);

            List<ReporteCarreraAnioDTO> reporteCarreraAnioDTOS = List.of();
            reporteCarreraAnioDTOS.addAll(query.getResultList());
               System.out.println(reporteCarreraAnioDTOS.toString());
            em.close();
            return reporteCarreraAnioDTOS;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}

