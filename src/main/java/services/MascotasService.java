package services;

import entities.Habilidad;
import entities.Mascota;
import jakarta.persistence.EntityManager;
import repositories.MascotaRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MascotasService {

    // Atributos

    private static final String filePath = "src/main/resources/mascotas.csv";

    private HabilidadesService habilidadesService;

    private MascotaRepository mascotaRepository;

    private List<Mascota> mascotas;


    // Constructores

    public MascotasService(EntityManager em) {
        this.mascotas = new ArrayList<>();
        this.habilidadesService = new HabilidadesService();
        this.mascotaRepository = new MascotaRepository(em);
    }


    // Getters y Setters

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }


    // Métodos

    public void mascotasFromFile() {
        File file = new File(filePath);

        try {
            Scanner sc = new Scanner(file);

            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Mascota mascota = mascotaFromLine(line);
                mascotas.add(mascota);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mascota mascotaFromLine(String line) {
        String[] values = line.split(",");

        //nombre,nivel_energia,nivel_humor,habilidades
        String nombre = values[0];
        double nivel_energia = Double.parseDouble(values[1]);

        String habilidadesNames = values[3];
        List<Habilidad> habilidades = habilidadesService.getOrCreateHabilidades(habilidadesNames);

        return new Mascota(nombre, nivel_energia, habilidades);
    }

    public int getCantidadMascotas() {
        return mascotas.size();
    }

//    public void mostrarMascotasVivas() {
//        for (Mascota mascota : this.mascotas) {
//            if (mascota.estaViva()) {
//                System.out.println(mascota);
//            }
//        }
//    }

    public void guardarVivas() throws IOException {

        List<Mascota> mascotasVivas = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            if (mascota.estaViva()) {
                mascotasVivas.add(mascota);
            }
        }

        mascotaRepository.saveAll(mascotasVivas);
        System.out.println("Entidades cargadas con éxito en la BD.");
    }

}
