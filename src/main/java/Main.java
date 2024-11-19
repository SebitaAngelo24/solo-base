import entities.Habilidad;
import entities.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repositories.JPAUtil;
import repositories.MascotaDAO;
import services.MascotasService;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotas-pu");
        EntityManager em = JPAUtil.getEntityManager();

        MascotasService mascotasService = new MascotasService(em);

        // Cargar datos del archivo csv
        mascotasService.mascotasFromFile();
        System.out.println("Cantidad Mascotas cargadas: " + mascotasService.getCantidadMascotas());

        // Mostrar mascotas vivas
        // mascotasService.mostrarMascotasVivas();

        // Testing Acciones
        Mascota mascotaA = mascotasService.getMascotas().get(6);
        System.out.println(mascotaA.getNombre());

        mascotaA.ejercitar();
        mascotaA.ingerirAlimentos();
        mascotaA.descansar(5);

        // Guardar mascotas vivas en la BD
        mascotasService.guardarVivas();

        // Consultas JPA

//        // Obtener primera mascota con id "x" - ANDA!
//        MascotaDAO mascotaDAO = new MascotaDAO();
//        Mascota mascota = mascotaDAO.obtenerMascotaConHabilidad(1);
//        System.out.println("Mascota: " + mascota.getNombre());
//
////        // Obtener todas las mascotas con id "x" - ANDA!
////        List<Mascota> mascotas = mascotaDAO.obtenerMascotasConHabilidad(1);
////        for (Mascota mascotita : mascotas) {
////            System.out.println("Nombre: " + mascotita.getNombre());
////            System.out.println("Habilidades:");
////            for (Habilidad habilidad : mascotita.getHabilidades()) {
////                System.out.println(" - " + habilidad.getNombre());
////            }
////            System.out.println();
////        }
//
//        // Find all de mascotas con habilidad "x" y las otras habilidades que tenga - ANDA!
//        List<Mascota> mascotas = mascotaDAO.obtenerMascotasConHabilidad(4);
//        for (Mascota mascotita : mascotas) {
//            System.out.println("Nombre: " + mascotita.getNombre());
//            System.out.println("Habilidades:");
//            for (Habilidad habilidad : mascotita.getHabilidades()) {
//                System.out.println(" - " + habilidad.getNombre());
//            }
//            System.out.println();
//        }




//        MascotaDAO mascotaDAO = new MascotaDAO();

//        // Guardar mascota individual - ANDA!
//        Mascota mascota1 = new Mascota("Mascota1", 10.0, List.of(new Habilidad("Habilidad1")));
//        mascotaDAO.guardarMascota(mascota1);
//        System.out.println("Mascota guardada: " + mascota1);
//
//        // Guardar una lista de mascotas - ANDA!
//        Mascota mascota2 = new Mascota("Mascota2", 5.0, List.of(new Habilidad("Habilidad2")));
//        Mascota mascota3 = new Mascota("Mascota3", 8.0, List.of(new Habilidad("Habilidad3")));
//        List<Mascota> mascotas = List.of(mascota2, mascota3);
//        mascotaDAO.guardarMascotas(mascotas);
//        System.out.println("Mascotas guardadas: " + mascotas);
//
//        // Obtener mascota por id - ANDA! REPETIDO!
//        Mascota mascotaConHabilidad = mascotaDAO.obtenerMascotaConHabilidad(1);
//        System.out.println("Mascota con habilidad: " + mascotaConHabilidad);
//
//        // Obtener mascotas con habilidad "x" y las otras habilidades que tenga - ANDA! REPETIDO!
//        List<Mascota> mascotasConHabilidad = mascotaDAO.obtenerMascotasConHabilidad(1);
//        System.out.println("Mascotas con habilidad: " + mascotasConHabilidad);
//
//        // Obtener todas las mascotas - ANDA!
//        MascotaDAO mascotitaDAO = new MascotaDAO();
//        List<Mascota> todasLasMascotas = mascotitaDAO.obtenerTodasLasMascotasConHabilidades();
//        System.out.println("Todas las mascotas:");
//        for (Mascota mascota : todasLasMascotas) {
//            System.out.println(mascota.toString());
//        }
//
//        // Actualizar mascota - ANDA!
//        Mascota mascota1 = new Mascota();
//        mascota1.setId(48); // Id de la mascota que deseas actualizar
//        mascota1.setNombre("Nuevo nombre");
//        mascotaDAO.actualizarMascota(mascota1);
//        System.out.println("Mascota actualizada: " + mascota1);

//        // Eliminar por id - ANDA!
//        mascotaDAO.eliminarMascota(50);
//        System.out.println("Mascota eliminada");

        JPAUtil.close();

    }
}
