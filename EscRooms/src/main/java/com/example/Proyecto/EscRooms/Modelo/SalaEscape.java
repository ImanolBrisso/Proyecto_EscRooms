package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/*
Se registran nuevas salas, las reservas, si se encuentran disponibles y capacidad
 */
@Entity
public class SalaEscape {
    @Id @GeneratedValue // Generar filtrados - @
    private String nombre;
    private int capacidad; // la capacidad se usa al interactuar en la consola
    private ArrayList<String> reservas;

    public SalaEscape(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.reservas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public boolean reservar(String fecha) {
        if (!reservas.contains(fecha)) {
            reservas.add(fecha);
            System.out.println("Ha reservado exitosamente a " + nombre + " el " + fecha);
            return true;
        } else {
            System.out.println("La sala " + nombre + " ya está reservada para la fecha del " + fecha);
            return false;
        }
    }

    public void mostrarDisponibilidad() {
        if (reservas.isEmpty()) {
            System.out.println("La sala " + nombre + " está disponible todos los días.");
        } else {
            System.out.println("La sala " + nombre + " tiene las siguientes reservas ocupadas:");
            for (String fecha : reservas) {
                System.out.println(fecha);
            }
        }
    }
}