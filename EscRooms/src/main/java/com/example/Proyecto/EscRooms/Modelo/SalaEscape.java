package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    // Verificar metodos Min,Max,DecimalMin en caso de agregar dependencia en archivo pom.xml

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
    @Transient // No persistimos esta lista en la base de datos
    private List<LocalDateTime> reservas = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public SalaEscape() {}

    // Constructor con los campos principales
    public SalaEscape(String nombre, String dificultad, int capacidadMin, int capacidadMax, BigDecimal precio, int duracionMinutos) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.capacidadMin = capacidadMin;
        this.capacidadMax = capacidadMax;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    // Constructor auxiliar
    public SalaEscape(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadMax = capacidad;
        this.capacidadMin = 1;
        this.duracionMinutos = 60; // Duración predeterminada
        this.precio = BigDecimal.ZERO; // Precio predeterminado
    }

    // Método para reservar la sala
    public void reservar(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            LocalDateTime fechaReserva = LocalDateTime.parse(fecha, formatter);

            if (reservas.contains(fechaReserva)) {
                throw new IllegalArgumentException("Esta fecha ya está reservada.");
            }

            reservas.add(fechaReserva);
            LOGGER.info(() -> String.format("Reserva realizada para la sala %s en la fecha %s", nombre, fecha));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
        }
    }

    // Getters y Setters
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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion != null ? descripcion.trim() : null;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        if (dificultad == null || dificultad.trim().isEmpty()) {
            throw new IllegalArgumentException("La dificultad es obligatoria");
        }
        this.dificultad = dificultad.trim();
    }

    public int getCapacidadMin() {
        return capacidadMin;
    }

    public void setCapacidadMin(int capacidadMin) {
        if (capacidadMin < 1) {
            throw new IllegalArgumentException("La capacidad mínima debe ser al menos 1");
        }
        this.capacidadMin = capacidadMin;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(int capacidadMax) {
        if (capacidadMax < 1) {
            throw new IllegalArgumentException("La capacidad máxima debe ser al menos 1");
        }
        this.capacidadMax = capacidadMax;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo o nulo");
        }
        this.precio = precio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        if (duracionMinutos < 15 || duracionMinutos > 180) {
            throw new IllegalArgumentException("La duración debe estar entre 15 y 180 minutos");
        }
        this.duracionMinutos = duracionMinutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    // Validación de capacidades
    @PrePersist
    @PreUpdate
    private void validarDatos() {
        validarCapacidades();
    }

    private void validarCapacidades() {
        if (capacidadMax < capacidadMin) {
            throw new IllegalStateException("La capacidad máxima no puede ser menor que la capacidad mínima");
        }
    }

    // equals y hashCode basados en 'id'
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaEscape that = (SalaEscape) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SalaEscape{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dificultad='" + dificultad + '\'' +
                ", capacidadMin=" + capacidadMin +
                ", capacidadMax=" + capacidadMax +
                ", precio=" + precio +
                ", duracionMinutos=" + duracionMinutos +
                ", activo=" + activo +
                '}';
    }
}

