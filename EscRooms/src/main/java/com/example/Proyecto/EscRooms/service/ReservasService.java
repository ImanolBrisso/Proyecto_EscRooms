package com.example.Proyecto.EscRooms.service;

import com.example.Proyecto.EscRooms.Modelo.Reservas;
import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import com.example.Proyecto.EscRooms.repository.ReservasRepository;
import com.example.Proyecto.EscRooms.repository.SalaEscapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservasService {
    private final ReservasRepository reservasRepository;
    private final SalaEscapeRepository salaEscapeRepository;

    @Autowired
    public ReservasService(ReservasRepository reservasRepository, SalaEscapeRepository salaEscapeRepository) {
        this.reservasRepository = reservasRepository;
        this.salaEscapeRepository = salaEscapeRepository;
    }

    public void agregarSala(SalaEscape salaEscape) {
        salaEscapeRepository.save(salaEscape);
    }

    public List<SalaEscape> obtenerTodasLasSalas() {
        return salaEscapeRepository.findAll();
    }

    public void reservarSala(Long salaId, LocalDateTime fecha, String clienteEmail) {
        // Confirma si la sala existe actualmente
        SalaEscape salaEscape = salaEscapeRepository.findById(salaId)
                .orElseThrow(() -> new IllegalArgumentException("Sala no encontrada"));

        // Confirma pero si ya existe una reserva para esa sala y esa hora especifica
        if (reservasRepository.existsByFechaReservaAndSalaEscapeId(fecha, salaId)) {
            throw new IllegalStateException("La sala ya se encuentra reservada para esa fecha y hora");
        }

        Reservas reserva = new Reservas(clienteEmail, fecha, salaEscape);
        reservasRepository.save(reserva);
    }

    // Se definen los parametros de búsqueda de reservas - Confirmados en reservas repository para su utilidad
    public List<Reservas> buscarReservasPorFecha(LocalDateTime fecha) { // se crea metodo pendiente en reservas repository de findbyfecharepository
        return reservasRepository.findByFechaReserva(fecha);
    }

    public List<Reservas> buscarReservasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservasRepository.findByFechaReservaBetween(fechaInicio, fechaFin);
    }

    public List<Reservas> buscarReservasPorSala(Long salaId) {
        return reservasRepository.findBySalaEscapeId(salaId);
    }

    public List<Reservas> buscarReservasPorCliente(String email) {
        return reservasRepository.findByClienteEmail(email);
    }

    public boolean verificarDisponibilidad(Long salaId, LocalDateTime fecha) {
        return !reservasRepository.existsByFechaReservaAndSalaEscapeId(fecha, salaId);
    }

    public void cancelarReserva(Long reservaId) {
        if (!reservasRepository.existsById(reservaId)) {
            throw new IllegalArgumentException("No se ha encontrado una reserva");
        }
        reservasRepository.deleteById(reservaId);
    }

    public List<Reservas> obtenerReservasFuturas(LocalDateTime ahora) {
        return reservasRepository.findByFechaReservaAfter(ahora);
    }

    public Optional<Reservas> obtenerReserva(Long reservaId) {
        return reservasRepository.findById(reservaId);
    }

    public List<Reservas> obtenerTodaslasReservas() {
        return reservasRepository.findAll(); // Busqueda de todas las salas
    }
}



