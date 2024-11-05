package com.example.Proyecto.EscRooms.repository;

import com.example.Proyecto.EscRooms.Modelo.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// principales confirmadas
@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {
    List<Reservas> findByFechaReservaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Reservas> findBySalaEscapeId(Long salaId);
    List<Reservas> findByClienteEmail(String email);
    boolean existsByFechaReservaAndSalaEscapeId(LocalDateTime fechaReserva, Long salaId);

    // opcionales confirmadas de momento - reservas services
    List<Reservas> findByFechaReservaAfter(LocalDateTime fecha);
    List<Reservas> findByFechaReserva(LocalDateTime fecha);
}


