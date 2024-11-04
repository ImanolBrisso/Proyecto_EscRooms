package com.example.Proyecto.EscRooms.controller;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import com.example.Proyecto.EscRooms.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    private final ReservasService reservasService;

    @Autowired
    public ReservasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @PostMapping("/agregar-sala")
    public String agregarSala(@RequestBody SalaEscape salaEscape) {
        reservasService.agregarSala(salaEscape);
        return "Sala agregada exitosamente";
    }

    @GetMapping("/salas")
    public List<SalaEscape> obtenerTodasLasSalas() {
        return reservasService.obtenerTodasLasSalas();
    }

    @PostMapping("/reservar")
    public String reservarSala(@RequestParam Long salaId,
                               @RequestParam String fechaReserva,
                               @RequestParam String clienteEmail) {
        LocalDateTime fecha = LocalDateTime.parse(fechaReserva); // Convierte el String a LocalDateTime
        reservasService.reservarSala(salaId, fecha, clienteEmail);
        return "Reserva realizada exitosamente";
    }
}

