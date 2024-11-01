package com.example.Proyecto.EscRooms.Modelo;

import java.util.ArrayList;

/*
Se toma en cuenta la cantidad de salas y la gestion de reservas para una sala especifica
 */

public class Reservas {
    // Generar filtrados - @
    private ArrayList<SalaEscape> salas;

    public Reservas() {
        this.salas = new ArrayList<>();
    }

    public void agregarSala(String nombre, int capacidad) {
        SalaEscape nuevaSala = new SalaEscape(nombre, capacidad);
        salas.add(nuevaSala);
        System.out.println("Sala " + nombre + " agregada para una capacidad de hasta " + capacidad + " personas como máximo.");
    }

    public SalaEscape buscarSala(String nombre) {
        for (SalaEscape sala : salas) {
            if (sala.getNombre().equalsIgnoreCase(nombre)) {
                return sala;
            }
        }
        System.out.println("No se ha encontrado ninguna sala.");
        return null;
    }

    public void mostrarSalas() {
        if (salas.isEmpty()) {
            System.out.println("No hay salas registradas actualmente.");
        } else {
            System.out.println("Salas disponibles:");
            for (SalaEscape sala : salas) {
                System.out.println("- " + sala.getNombre());
            }
        }
    }

    public void reservarSala(String nombreSala, String fecha) {
        SalaEscape sala = buscarSala(nombreSala); // verificar tipifacion de las fechas correctamente
        if (sala != null) {
            sala.reservar(fecha);
        }
    }
}