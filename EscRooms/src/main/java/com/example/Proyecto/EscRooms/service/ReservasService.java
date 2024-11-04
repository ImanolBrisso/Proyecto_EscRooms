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
        salaEscapeRepository.save(salaEscape);  // Usar SalaEscapeRepository en lugar de ReservasRepository
    }

    public Optional<SalaEscape> buscarSalaPorNombre(String nombre) {
        return salaEscapeRepository.findByNombreIgnoreCase(nombre);
    }

    public List<SalaEscape> obtenerTodasLasSalas() {
        return salaEscapeRepository.findAll();
    }

    public void reservarSala(Long salaId, LocalDateTime fechaReserva, String clienteEmail) {
        if (reservasRepository.existsByFechaReservaAndSalaEscapeId(fechaReserva, salaId)) {
            throw new IllegalArgumentException("La sala ya está reservada para esa fecha y hora.");
        }
        Reservas nuevaReserva = new Reservas();
        nuevaReserva.setSalaEscape(new SalaEscape());
        nuevaReserva.setFechaReserva(fechaReserva);
        nuevaReserva.setClienteEmail(clienteEmail);
        reservasRepository.save(nuevaReserva);
    }
}


