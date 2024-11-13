package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*; // javax reemplazado por jakarta - librerias

@Entity
@Table(name = "salas_escape")

public class SalaEscape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int capacidad;
    private double precio;
    private boolean activo;
    private String dificultad; // comprobar dificultad en repository

    // Constructor vacío para JPA
    public SalaEscape() {}

    public SalaEscape(String nombre, int capacidad, double precio, boolean activo,String dificultad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precio = precio;
        this.activo = activo;
        this.dificultad = dificultad;
    }

    // Se completan getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}



