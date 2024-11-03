package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

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

    @Min(value = 1, message = "La capacidad mínima debe ser al menos 1")
    @Column(name = "capacidad_min", nullable = false)
    private int capacidadMin;

    @Min(value = 1, message = "La capacidad máxima debe ser al menos 1")
    @Column(name = "capacidad_max", nullable = false)
    private int capacidadMax;

    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private BigDecimal precio;

    @Min(value = 15, message = "La duración mínima debe ser de 15 minutos")
    @Max(value = 180, message = "La duración máxima debe ser de 180 minutos")
    @Column(name = "duracion_minutos", nullable = false)
    private int duracionMinutos;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Transient
    private static final Logger LOGGER = Logger.getLogger(SalaEscape.class.getName());

    // Lista de reservas
    @Transient // No persistimos esta lista en la base de datos en este caso
    private List<LocalDateTime> reservas = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public SalaEscape() {}

    public SalaEscape(String nombre, String dificultad, int capacidadMin, int capacidadMax, BigDecimal precio, int duracionMinutos) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.capacidadMin = capacidadMin;
        this.capacidadMax = capacidadMax;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    public SalaEscape(String nombre, int capacidad) {
    }

    // Método para reservar la sala
    public void reservar(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime fechaReserva = LocalDateTime.parse(fecha, formatter);

        if (reservas.contains(fechaReserva)) {
            throw new IllegalArgumentException("Esta fecha ya está reservada.");
        }

        reservas.add(fechaReserva);
        LOGGER.info(() -> String.format("Reserva realizada para la sala %s en la fecha %s", nombre, fecha));
    }

    // Metodo getNombre
    public String getNombre() {
        return nombre;
    }

    // Metodo SetId de la Sala Controller
    public void setId(Long id) {
    }

}
