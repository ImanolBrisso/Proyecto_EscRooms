package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "salas_escape")
public class SalaEscape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private String dificultad;

    @Column(nullable = false)
    private int capacidadMin;

    @Column(nullable = false)
    private int capacidadMax;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(name = "duracion_minutos", nullable = false)
    private int duracionMinutos;

    @Column(nullable = false)
    private boolean activo;

    public void setId(Long id) {
    }

    // Creados los siguientes parametros getters y setters.
}



