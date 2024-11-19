package repositories;

import entities.Habilidad;
import entities.HistoriaMascota;
import entities.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MascotaDAO {

    //Metodo para guardar mascota individual
    public void guardarMascota(Mascota mascota) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            //Guardar la mascota
            em.persist(mascota);

            //Persistir habilidades
            for (Habilidad habilidad : mascota.getHabilidades()) {
                /*Habilidad habilidadExistente = em.find(Habilidad.class, habilidad);
                if(habilidadExistente != null) {
                }*/
                em.persist(habilidad);
            }

            //Persistir historico
            for (HistoriaMascota historiaMascota : mascota.getHistoricoAcciones()) {
                em.persist(historiaMascota);
            }
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Metodo para guardar una lista de mascotas
    public void guardarMascotas(List<Mascota> mascotas) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            for (Mascota mascota : mascotas) {
                //Guardar cada mascota
                em.persist(mascota);

                //Guardar habilidades
                for(Habilidad habilidad : mascota.getHabilidades()) {
                    /*Habilidad habilidadExistente = em.find(Habilidad.class, habilidad);
                    if(habilidadExistente != null) {
                    }*/
                    em.persist(habilidad);
                }

                //Guardar historico
                for (HistoriaMascota historiaMascota : mascota.getHistoricoAcciones()) {
                    em.persist(historiaMascota);
                }
            }
            tx.commit();
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Find by id de mascota
    public Mascota obtenerMascotaConHabilidad(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Mascota> query = em.createQuery(
                    "SELECT m FROM Mascota m LEFT JOIN FETCH m.habilidades WHERE m.id = :id", Mascota.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

//    //Find all de mascotas con habilidad "x"
//    public List<Mascota> obtenerMascotasConHabilidad(int id) {
//        EntityManager em = JPAUtil.getEntityManager();
//        try {
//            TypedQuery<Mascota> query = em.createQuery(
//                    "SELECT DISTINCT m FROM Mascota m LEFT JOIN FETCH m.habilidades h WHERE h.id = :id", Mascota.class
//            );
//            query.setParameter("id", id);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }

    //Find all de mascotas con habilidad "x" y las otras habilidades que tenga
    public List<Mascota> obtenerMascotasConHabilidad(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT m.nombre, h.nombre " +
                            "FROM Mascota m " +
                            "JOIN m.habilidades h " +
                            "WHERE h.id = :id " +
                            "GROUP BY m.id, h.id", Object[].class
            );
            query.setParameter("id", id);
            List<Object[]> resultados = query.getResultList();

            Map<String, List<String>> mascotas = new HashMap<>();
            for (Object[] resultado : resultados) {
                String nombreMascota = (String) resultado[0];
                String nombreHabilidad = (String) resultado[1];
                mascotas.computeIfAbsent(nombreMascota, k -> new ArrayList<>()).add(nombreHabilidad);
            }

            List<Mascota> mascotasConHabilidades = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : mascotas.entrySet()) {
                String nombreMascota = entry.getKey();
                List<String> habilidades = entry.getValue();
                List<Habilidad> habilidadesObj = new ArrayList<>();
                for (String habilidad : habilidades) {
                    habilidadesObj.add(new Habilidad(habilidad));
                }
                mascotasConHabilidades.add(new Mascota(nombreMascota, 0.0, habilidadesObj));
            }

            return mascotasConHabilidades;
        } finally {
            em.close();
        }
    }

    //Find all de mascotas
    public List<Mascota> obtenerTodasLasMascotasConHabilidades() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Mascota> query = em.createQuery("SELECT m FROM Mascota m JOIN FETCH m.habilidades", Mascota.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    //Actualizar mascota
    public void actualizarMascota(Mascota mascota) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(mascota); //Actualiza la mascota
            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Eliminar por id
    public void eliminarMascota(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Mascota mascota = em.find(Mascota.class, id);
            if(mascota != null) {
                em.remove(mascota); //Elimina la mascota
            }
            tx.commit();
        } catch (Exception e) {
            if(tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
