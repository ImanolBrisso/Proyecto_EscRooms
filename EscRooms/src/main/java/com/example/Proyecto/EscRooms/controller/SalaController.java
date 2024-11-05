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

// AGREGAR LISTAS DE SALAS PENDIENTES DEL REPOSITORIO DE SALAESCAPE

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaEscapeRepository salaRepository;

    // En el apartado principal se visualizan todas las salas
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

    // Se muestran especificaciones de la sala seleccionada
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

    // Para Crear nueva sala
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("sala", new SalaEscape());
        model.addAttribute("titulo", "Crear Nueva Sala");
        return "salas/formulario";
    }

    // Secuencia que procede a crear la Sala
    @PostMapping("/crear")
    public String createSala(@ModelAttribute SalaEscape sala, RedirectAttributes redirectAttributes) {
        try {
            salaRepository.save(sala);
            redirectAttributes.addFlashAttribute("respuesta", "Sala creada exitosamente");
            return "redirect:/salas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la sala");
            return "redirect:/salas/crear";
        }
    }

    // Editar Sala
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

    // Secuencia que procede ae editar la Sala
    @PostMapping("/editar/{id}")
    public String updateSala(@PathVariable Long id, @ModelAttribute SalaEscape sala,
                             RedirectAttributes redirectAttributes) {
        try {
            if (salaRepository.existsById(id)) {
                sala.setId(id);  // Asegurarse de mantener el mismo ID
                salaRepository.save(sala);
                redirectAttributes.addFlashAttribute("respuesta", "Sala actualizada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sala no encontrada");
            }
            return "redirect:/salas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la sala");
            return "redirect:/salas/editar/" + id;
        }
    }

    // Para eliminar Sala
    @PostMapping("/eliminar/{id}")
    public String deleteSala(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (salaRepository.existsById(id)) {
                salaRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("respuesta", "Sala eliminada exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Sala no encontrada");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la sala");
        }
        return "redirect:/salas";
    }

    // Busqueda de salas por dificultad - verificado en SalaEscapeRepository en estilo poder visualziarlo con "candados" - "calaveras"
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

    // Se menajen los errores a tener en cuenta
    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", "Ha ocurrido un error: " + e.getMessage());
        return "error";
    }

}

