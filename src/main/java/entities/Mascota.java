package entities;

import exceptions.MascotaMuertaException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
//import java.util.Objects;


@Entity @Table(name = "Mascotas")
public class Mascota {

    // Atributos

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel_energia")
    private double nivel_energia;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Habilidades_x_Mascota",
            joinColumns = @JoinColumn(name = "id_mascota"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> habilidades;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    private List<HistoriaMascota> historicoAcciones;

//    private int contadorIngestas;
//    private int contadorIngestasConsecutivas;
//    private int contadorEjercitaciones;
//    private int contadorEjercitacionesConsecutivas;
//    private int maxRachaFitness;


    // Constructores

    public Mascota() {
    }

    public Mascota(String nombre,
                   double nivel_energia,
                   List<Habilidad> habilidades)
    {
        this.nombre = nombre;
        this.nivel_energia = nivel_energia;
        this.habilidades = habilidades;
        this.historicoAcciones = new ArrayList<>();
    }


    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }


    public double getNivel_energia() {
        return nivel_energia;
    }

    public void setNivel_energia(double nivel_energia) {
        this.nivel_energia = nivel_energia;
    }


    public List<HistoriaMascota> getHistoricoAcciones() {
        return historicoAcciones;
    }

    // Métodos

    public boolean estaViva() {
        return nivel_energia > 0;
    }

    public void ingerirAlimentos() {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no es factible que haga algo.");
        }

        double energiaInicio = this.nivel_energia;

        this.nivel_energia = (this.nivel_energia + 10) >= 100 ? 100 : (this.nivel_energia + 10);

        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Alimentar", energiaInicio, this.nivel_energia, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

    public void ejercitar() {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no es factible que haga algo.");
        }

        double energiaInicio = this.nivel_energia;

        this.nivel_energia = (this.nivel_energia - 5) <= 0 ? 0 : (this.nivel_energia - 5);

        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, se quedó sin energía");
        }

        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Ejercitar", energiaInicio, this.nivel_energia, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

    public void descansar(int n) {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no es factible que haga algo.");
        }

        double energiaInicio = this.nivel_energia;

        this.nivel_energia = (this.nivel_energia + (n * 0.5)) >= 100 ? 100 : (this.nivel_energia + (n * 0.5));

        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Ejercitar", energiaInicio, this.nivel_energia, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

//    public void demostrarHabilidad(String habilidadName) {
//        if (!estaViva()) {
//            throw new MascotaMuertaException("La mascota está muerta, no puede realizar acciones.");
//        }
//
//        int energiaInicio = this.nivel_energia;
//        int humorInicio = this.nivel_humor;
//
//        contadorIngestasConsecutivas = 0;
//        contadorEjercitacionesConsecutivas = 0;
//
//        for (Habilidad habilidad : this.habilidades) {
//            if (Objects.equals(habilidad.getNombre(), habilidadName)) {
//                System.out.println("Mirá como puedo " + habilidadName + "!");
//                this.nivel_humor++;
//            } else {
//                throw new IllegalArgumentException("La habilidad '" + habilidadName + "' no existe.");
//            }
//        }
//        LocalDateTime fechaHora = LocalDateTime.now();
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        HistoriaMascota historia = new HistoriaMascota(this, "DemostrarHabilidad", energiaInicio, humorInicio, this.nivel_energia, this.nivel_humor, fechaHora.format(formato));
//        this.historicoAcciones.add(historia);
//    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre: " + nombre +
                " | nivel de energía: " + nivel_energia  +
                " | habilidades: " + habilidades +  '}';
//                + '\'' +
//                ", estado fisico='" + getEstadoFisico() + '\'' +
//                ", racha fitness='" + getRachaFitness() + '\'' +
//                '}';
    }
}
