package repository;

import com.opencsv.CSVReader;
import dto.CarreraDTO;
import entities.Carrera;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;

import java.io.FileReader;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository{
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
    public List<CarreraDTO> getCarrerasActivas() {
        return List.of();
    }
}
