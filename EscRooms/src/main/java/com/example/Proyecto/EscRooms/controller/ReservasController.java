package com.example.Proyecto.EscRooms.controller;


import com.example.Proyecto.EscRooms.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

    private final ReservasService reservasService;

    @Autowired
    public ReservasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @PostMapping("/agregar-sala")
    public <SalaEscape> String agregarSala(@ModelAttribute SalaEscape salaEscape, Model model) {
        reservasService.agregarSala((com.example.Proyecto.EscRooms.Modelo.SalaEscape) salaEscape);
        model.addAttribute("mensaje", "Sala agregada exitosamente");
        return "sala-agregada"; // Vista para confirmar la adición de la sala
    }

    @GetMapping("/salas")
    public <SalaEscape> String obtenerTodasLasSalas(Model model) {
        List<SalaEscape> salas = (List<SalaEscape>) reservasService.obtenerTodasLasSalas();
        model.addAttribute("salas", salas);
        return "lista-salas"; // Vista que muestra la lista de salas
    }

    @PostMapping("/reservar")
    public String reservarSala(@RequestParam Long salaId,
                               @RequestParam String fechaReserva,
                               @RequestParam String clienteEmail,
                               Model model) {
        LocalDateTime fecha = LocalDateTime.parse(fechaReserva); // Convierte el String a LocalDateTime
        reservasService.reservarSala(salaId, fecha, clienteEmail);
        model.addAttribute("mensaje", "Reserva realizada exitosamente");
        return "reserva-confirmada"; // Vista que confirma la reserva
    }
}



