package com.example.Proyecto.EscRooms.Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/* Clase que gestiona las reservas de las clases
 * Agregar, búsqueda y muestra
 */
public class Reservas {
    private static final Logger LOGGER = Logger.getLogger(Reservas.class.getName());
    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final List<SalaEscape> salas;

    public Reservas() {
        this.salas = new ArrayList<>();
    }

    /* Se procede a agregar una nueva sala de escape.

     Anotaciones a tener en cuenta:
     * @param nombre Nombre de la sala
     * @param capacidad Capacidad máxima de personas
     * @throws IllegalArgumentException si el nombre está vacío o la capacidad es inválida
     */
    public void agregarSala(String nombre, int capacidad) {
        validarDatosSala(nombre, capacidad);

        if (existeSala(nombre)) {
            throw new IllegalArgumentException("Ya existe una sala con el nombre: " + nombre);
        }

        SalaEscape nuevaSala = new SalaEscape(nombre, capacidad);
        salas.add(nuevaSala);
        LOGGER.info(() -> String.format("Sala %s agregada con capacidad de %d personas", nombre, capacidad));
    }

    /**
     * Busca una sala por su nombre.
     * @param nombre Nombre de la sala a buscar
     * @return Optional con la sala si existe
     */
    public Optional<SalaEscape> buscarSala(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sala no puede estar vacío");
        }

        return salas.stream()
                .filter(sala -> sala.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst();
    }

    /**
     * Muestra todas las salas disponibles.
     * @return Lista de nombres de salas disponibles
     */
    public List<String> obtenerSalasDisponibles() {
        if (salas.isEmpty()) {
            LOGGER.info("No hay salas registradas actualmente.");
            return new ArrayList<>();
        }

        List<String> salasList = new ArrayList<>();
        for (SalaEscape sala : salas) {
            salasList.add(sala.getNombre());
        }
        return salasList;
    }

    /**
     * Realiza una reserva para una sala específica.
     * @param nombreSala Nombre de la sala a reservar
     * @param fecha Fecha y hora de la reserva en formato dd/MM/yyyy HH:mm
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public void reservarSala(String nombreSala, String fecha) {
        if (nombreSala == null || fecha == null) {
            throw new IllegalArgumentException("El nombre de la sala y la fecha son obligatorios");
        }

        Optional<SalaEscape> salaOptional = buscarSala(nombreSala);
        if (salaOptional.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la sala: " + nombreSala);
        }

        try {
            LocalDateTime fechaReserva = LocalDateTime.parse(fecha, FECHA_FORMATTER);
            if (fechaReserva.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("No se pueden hacer reservas para fechas pasadas");
            }

            salaOptional.get().reservar(fecha);
            LOGGER.info(() -> String.format("Reserva realizada para la sala %s en la fecha %s", nombreSala, fecha));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
        }
    }

    private void validarDatosSala(String nombre, int capacidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sala no puede estar vacío");
        }
        if (capacidad <= 0 || capacidad > 50) {  // Asumiendo un límite máximo razonable
            throw new IllegalArgumentException("La capacidad debe ser un número positivo y menor a 50");
        }
    }

    private boolean existeSala(String nombre) {
        return salas.stream()
                .anyMatch(sala -> sala.getNombre().equalsIgnoreCase(nombre.trim()));
    }
}