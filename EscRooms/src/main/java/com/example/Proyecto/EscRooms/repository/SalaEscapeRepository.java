package com.example.Proyecto.EscRooms.repository;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaEscapeRepository extends JpaRepository<SalaEscape, Long> {

    // Búsqueda de salas (principales a tener en cuenta)

    List<SalaEscape> findByCapacidadGreaterThanEqual(int capacidad);
    List<SalaEscape> findByCapacidadLessThanEqual(int capacidad);
    List<SalaEscape> findByActivoTrue();
    List<SalaEscape> findByPrecioBetween(double precioMin, double precioMax);


    // Búsqueda para salas (posibles a añadir)

    List<SalaEscape> findByDificultad(String dificultad);
    Optional<SalaEscape> findByNombre(String nombre);
    List<SalaEscape> findByNombreContainingIgnoreCase(String nombre); // verificar posibilidad de sumarlo bajo el mismo filtro de "ByNombre" en SalaController
    List<SalaEscape> findByPrecioLessThanEqual(double precio);
    List<SalaEscape> findByPrecioGreaterThanEqual(double precio);


}
