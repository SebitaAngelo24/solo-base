package repositories;

import entities.Mascota;
import jakarta.persistence.EntityManager;

import java.util.List;


public class MascotaRepository {

    // Atributos

    private final EntityManager em;


    // Constructores

    public MascotaRepository(EntityManager em) {
        this.em = em;
    }


    // Métodos

    public void saveAll(List<Mascota> mascotasList) {
        mascotasList.forEach(this::save);
    }

    public void save(Mascota mascota) {
        em.getTransaction().begin();
        em.persist(mascota);
        em.getTransaction().commit();
    }


//    public void save(Mascota mascota) {
//        begin();
//        em.persist(mascota);
//        commit();
//    }
//
//    private void begin() {
//        em.getTransaction().begin();
//    }
//
//    private void commit() {
//        em.getTransaction().commit();
//    }
//
//    public List<Mascota> getFelices() {
//        String consulta = "SELECT m FROM Mascota m WHERE m.nivel_humor > :nivelHumor";
//        return em.createQuery(consulta, Mascota.class).setParameter("nivelHumor", 3).getResultList();
//    }
}
