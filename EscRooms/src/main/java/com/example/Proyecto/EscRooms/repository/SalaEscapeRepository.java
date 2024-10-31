package com.example.Proyecto.EscRooms.repository;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaEscapeRepository extends JpaRepository<SalaEscape, Long> {

    // Busqueda de sala por dificultad
    List<SalaEscape> findByDificultad(String dificultad);

    // Busqueda de sala por capacidad ya sea minima o máxima
    List<SalaEscape> findByCapacidadMinLessThanEqualAndCapacidadMaxGreaterThanEqual(int personas, int personas2);

    // Búsqueda de sala por nombre
    Optional<SalaEscape> findByNombreContainingIgnoreCase(String nombre);

    // Búsqueda de salas disponibles
    List<SalaEscape> findByActivoTrue();

    // Búsqueda de salas por precio
    List<SalaEscape> findByPrecioBetween(double precioMin, double precioMax);

}
