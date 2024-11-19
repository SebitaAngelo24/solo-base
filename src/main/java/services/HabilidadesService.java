package services;

import entities.Habilidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HabilidadesService {

    // Atributos

    Map<String, Habilidad> habilidades;


    // Constructores

    public HabilidadesService() {
        habilidades = new HashMap<>();
    }


    // Métodos

    // CHEQUEAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Habilidad> getOrCreateHabilidades(String names) {

        List<Habilidad> habilidadesArray = new ArrayList<>();
        String[] habilidadesNames = names.split(";");

        for (String habilidadName : habilidadesNames) {
            if (habilidades.containsKey(habilidadName)) {
                habilidadesArray.add(habilidades.get(habilidadName));
            } else {
                Habilidad habilidad = new Habilidad(habilidadName);
                habilidades.put(habilidadName, habilidad);
                habilidadesArray.add(habilidad);
            }
        }

        return habilidadesArray;
    }
}
