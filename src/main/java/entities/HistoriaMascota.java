package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity @Table(name = "Historia_Mascota")
public class HistoriaMascota {

    // Atributos

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @Column(name = "tipo_historico")
    private String tipo_historico;

    @Column(name = "energia_inicio")
    private double energia_inicio;

    @Column(name = "energia_fin")
    private double energia_fin;

    @Column(name = "fecha_hora")
    private String fecha_hora;


    // Constructores

    public HistoriaMascota() {
    }

    public HistoriaMascota(Mascota mascota,
                           String tipo_historico,
                           double energia_inicio,
                           double energia_fin,
                           String fecha_hora)
    {
        this.mascota = mascota;
        this.tipo_historico = tipo_historico;
        this.energia_inicio = energia_inicio;
        this.energia_fin = energia_fin;
        this.fecha_hora = fecha_hora;
    }


    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mascota getId_mascota() {
        return mascota;
    }

    public void setId_mascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getTipo_historico() {
        return tipo_historico;
    }

    public void setTipo_historico(String tipo_historico) {
        this.tipo_historico = tipo_historico;
    }

    public double getEnergia_inicio() {
        return energia_inicio;
    }

    public void setEnergia_inicio(double energia_inicio) {
        this.energia_inicio = energia_inicio;
    }

    public double getEnergia_fin() {
        return energia_fin;
    }

    public void setEnergia_fin(double energia_fin) {
        this.energia_fin = energia_fin;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
