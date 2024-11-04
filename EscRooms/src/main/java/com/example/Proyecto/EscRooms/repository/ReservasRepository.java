package com.example.Proyecto.EscRooms.repository;

import com.example.Proyecto.EscRooms.Modelo.Reservas;
import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {

    // Se generan Filtros

    // Búsqueda de reservas por fecha
    List<Reservas> findByFechaReservaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Búsqueda de reservas por Id de sala
    List<Reservas> findBySalaEscapeId(Long salaId);

    // Búsqueda de reservas por cliente o usuario
    List<Reservas> findByClienteEmail(String email);

    // Búsqueda y confirmación de reserva por horarios y fecha
    boolean existsByFechaReservaAndSalaEscapeId(LocalDateTime fechaReserva, Long salaId);

    // Confirmación y conteo de reservas por sala (usando solo el salaId)
    Long countBySalaEscapeId(Long salaId);

    boolean existsByNombreIgnoreCase(String trim);

    Optional<SalaEscape> findByNombreIgnoreCase(String trim);
}

