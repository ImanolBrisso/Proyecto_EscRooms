package com.example.Proyecto.EscRooms.controller;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import com.example.Proyecto.EscRooms.repository.SalaEscapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaEscapeRepository salaRepository;

    // Página principal - Lista todas las salas
    @GetMapping
    public String getAllSalas(Model model) {
        try {
            List<SalaEscape> salas = salaRepository.findAll();
            model.addAttribute("salas", salas);
            model.addAttribute("titulo", "Listado de Salas de Escape");
            return "salas/lista";  // retorna el nombre de la vista (lista.html)
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar las salas");
            return "error";
        }
    }

    // Mostrar detalles de una sala específica
    @GetMapping("/{id}")
    public String getSalaById(@PathVariable("id") Long id, Model model) {
        Optional<SalaEscape> salaData = salaRepository.findById(id);
        if (salaData.isPresent()) {
            model.addAttribute("sala", salaData.get());
            return "salas/detalle";
        } else {
            model.addAttribute("error", "Sala no encontrada");
            return "error";
        }
    }

    // Formulario para crear nueva sala
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("sala", new SalaEscape());
        model.addAttribute("titulo", "Crear Nueva Sala");
        return "salas/formulario";
    }

    // Procesar la creación de una nueva sala
    @PostMapping("/crear")
    public String createSala(@ModelAttribute SalaEscape sala, RedirectAttributes redirectAttributes) {
        try {
            salaRepository.save(sala);
            redirectAttributes.addFlashAttribute("mensaje", "Sala creada exitosamente");
            return "redirect:/salas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la sala");
            return "redirect:/salas/crear";
        }
    }

    // Formulario para editar sala
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<SalaEscape> salaData = salaRepository.findById(id);
        if (salaData.isPresent()) {
            model.addAttribute("sala", salaData.get());
            model.addAttribute("titulo", "Editar Sala");
            return "salas/formulario";
        } else {
            model.addAttribute("error", "Sala no encontrada");
            return "error";
        }
    }

    // Procesar la edición de una sala
    @PostMapping("/editar/{id}")
    public String updateSala(@PathVariable Long id, @ModelAttribute SalaEscape sala,
                             RedirectAttributes redirectAttributes) {
        try {
            if (salaRepository.existsById(id)) {
                sala.setId(id);  // Asegurarse de mantener el mismo ID
                salaRepository.save(sala);
                redirectAttributes.addFlashAttribute("mensaje", "Sala actualizada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sala no encontrada");
            }
            return "redirect:/salas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la sala");
            return "redirect:/salas/editar/" + id;
        }
    }

    // Eliminar sala
    @PostMapping("/eliminar/{id}")
    public String deleteSala(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (salaRepository.existsById(id)) {
                salaRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("mensaje", "Sala eliminada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sala no encontrada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la sala");
        }
        return "redirect:/salas";
    }

    // Buscar salas por dificultad
    @GetMapping("/dificultad/{dificultad}")
    public String getSalasByDificultad(@PathVariable String dificultad, Model model) {
        try {
            List<SalaEscape> salas = salaRepository.findByDificultad(dificultad);
            model.addAttribute("salas", salas);
            model.addAttribute("dificultadActual", dificultad);
            model.addAttribute("titulo", "Salas de dificultad: " + dificultad);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por dificultad");
            return "error";
        }
    }

    // Método para manejar errores generales
    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", "Ha ocurrido un error: " + e.getMessage());
        return "error";
    }
}

