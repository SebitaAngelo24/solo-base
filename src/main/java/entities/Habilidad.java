package entities;

import jakarta.persistence.*;

@Entity @Table(name = "Habilidades")
public class Habilidad {

    // Atributos

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_habilidad")
    private String nombre;


    // Constructores

    public Habilidad() {
    }

    public Habilidad(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre;
    }
}
