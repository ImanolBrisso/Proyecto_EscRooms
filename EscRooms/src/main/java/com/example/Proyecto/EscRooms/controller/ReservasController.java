package com.example.Proyecto.EscRooms.controller;


import com.example.Proyecto.EscRooms.Modelo.Reservas;
import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
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

    // Ver si se mantienen metodos o se borran
    @PostMapping("/agregar-sala")
    public <SalaEscape> String agregarSala(@ModelAttribute SalaEscape salaEscape, Model model) {
        reservasService.agregarSala((com.example.Proyecto.EscRooms.Modelo.SalaEscape) salaEscape);
        model.addAttribute("mensaje", "Sala agregada exitosamente");
        return "sala-agregada"; // Visualización confirmar la adición de la sala
    }

    @GetMapping("/salas")
    public <SalaEscape> String obtenerTodasLasSalas(Model model) {
        List<SalaEscape> salas = (List<SalaEscape>) reservasService.obtenerTodasLasSalas();
        model.addAttribute("salas", salas);
        return "lista-salas"; // Visualización de la lista de salas
    }

    // Get y Post agregados para reservar sala
    @GetMapping ("/reservar")
    public String MostrarFormularioReserva (Model model) {
        model.addAttribute("salas", reservasService.obtenerTodasLasSalas());
        model.addAttribute("reservas", reservasService.obtenerTodaslasReservas());
        return "reservar_sala";
    }

    @PostMapping("/reservar")
    public String reservarSala(@RequestParam Long salaId,
                               @RequestParam String fechaReserva,// convierte el string a LocalDate abajo
                               @RequestParam String clienteEmail,
                               Model model) {
        LocalDateTime fecha = LocalDateTime.parse(fechaReserva); // Convierte el String a LocalDateTime
        reservasService.reservarSala(salaId, fecha, clienteEmail);
        model.addAttribute("mensaje", "Reserva realizada exitosamente");
        return "reservar_sala"; // Se visualiza la reserva confirmada -  vista html
    }


}



