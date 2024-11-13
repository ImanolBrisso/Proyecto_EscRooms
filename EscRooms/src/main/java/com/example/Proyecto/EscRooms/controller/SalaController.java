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

    // Filtrado de búsqueda de salas - conectadas a SalaEscapeRepository

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

    // Verificar by nombre - salas repository

    @GetMapping("/nombre/{nombre}")
    public String getSalasByNombre(@PathVariable String nombre, Model model) {
        try {
            Optional<SalaEscape> salas = salaRepository.findByNombre(nombre); // Se confirma Optional y no List - ya que puede contener un valor o no, Lis en cambio colecciona e indentifica objetos

            if (salas.isPresent()) {
                model.addAttribute("salas", salas.get());
            } else {
                model.addAttribute("respuesta", "No se encontraron salas con el nombre: " + nombre);
            }
            // Se necesitaba hacer una verificación previa antes de pasarlo a "modelo"

            model.addAttribute("titulo", "Salas de nombre: " + nombre);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por nombre");
            return "error";  // Retorna una vista de error (asegúrate de tener una vista "error.html")
        }
    }

    // Ambos Metodos de Capacidad - Verificar ya que genera conflicto de utilizacion

    @GetMapping("/capacidad/{capacidad}")
    public String getSalasByCapacidad(@PathVariable Long capacidad, Model model) {
        try {
            List<SalaEscape> salas = salaRepository.findByCapacidadLessThanEqual(Math.toIntExact(capacidad));
            model.addAttribute("salas", salas);
            model.addAttribute("titulo", "Salas de capacidad: " + capacidad);
            model.addAttribute("capacidadActual", capacidad);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por capacidad");
            return "error";
        }

    }

    @GetMapping("/capacidad/{capacidad}")
    public String getSalasByCapacidad(@PathVariable int capacidad, Model model) {
        try {
            List<SalaEscape> salasPorCapacidad = salaRepository.findByCapacidadGreaterThanEqual(capacidad);
            model.addAttribute("salas", salasPorCapacidad);
            model.addAttribute("titulo", "Salas con capacidad mayor o igual a " + capacidad);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por capacidad");
            return "error";
        }
    }


    // Metodo utilizado: List<SalaEscape> findByActivoTrue() - SalaEscapeRepository - Anotacion
    @GetMapping("/salas/activas")
    public String getSalasActivas(Model model) {
        try {
            List<SalaEscape> salasActivas = salaRepository.findByActivoTrue();
            model.addAttribute("salas", salasActivas);
            model.addAttribute("titulo", "Salas Activas");
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas activas");
            return "error";
        }
    }

    // Metodo utilizado findByPrecioBetween
    @GetMapping("/salas/precio")
    public String getSalasByPrecio(@RequestParam double precioMin, @RequestParam double precioMax, Model model) {
        try {
            List<SalaEscape> salasPorPrecio = salaRepository.findByPrecioBetween(precioMin, precioMax);
            model.addAttribute("salas", salasPorPrecio);
            model.addAttribute("titulo", "Salas con precio entre " + precioMin + " y " + precioMax);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por rango de precio");
            return "error";
        }
    }

    // Ambos Metodos de Precio
    // LessThanEqual
    @GetMapping("/precio/menor-o-igual")
    public String getSalasByPrecioMax(@RequestParam double precio, Model model) {
        try {
            List<SalaEscape> salasPorPrecioMax = salaRepository.findByPrecioLessThanEqual(precio);
            model.addAttribute("salas", salasPorPrecioMax);
            model.addAttribute("titulo", "Salas con precio menor o igual a " + precio);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por precio máximo");
            return "error";
        }
    }
    // PrecioGreaterThanEqual
    @GetMapping("/precio/mayor-o-igual")
    public String getSalasByPrecioMin(@RequestParam double precio, Model model) {
        try {
            List<SalaEscape> salasPorPrecioMin = salaRepository.findByPrecioGreaterThanEqual(precio);
            model.addAttribute("salas", salasPorPrecioMin);
            model.addAttribute("titulo", "Salas con precio mayor o igual a " + precio);
            return "salas/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar salas por precio mínimo");
            return "error";
        }
    }
}

