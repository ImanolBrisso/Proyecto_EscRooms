package com.example.Proyecto.EscRooms.controller;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
import com.example.Proyecto.EscRooms.repository.SalaEscapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model; // a verificar
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.Map; // interfaz para coleccion de datos clave, valor (put,get, remove)
import java.util.HashMap; // recuperacion de los valores y datos mencionados anteriormente

// Utilizar Controller - Modelo MVC - con verificación en clases de Modelo --
@RestController
/* Se utiliza RestController es una combinacion de Controller y Responsebody retornando datos en formas JSON o XML
en vez de vistas como en el Controller - "retornan datos directamente al cliente en el cuerpo de la respuesta (generalmente en JSON),
sin necesidad de @ResponseBody en cada metodo
 */

@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaEscapeRepository salaRepository;

    // Visualización de las salas
    @GetMapping
    public ResponseEntity<List<SalaEscape>> getAllSalas() {
        try {
            List<SalaEscape> salas = salaRepository.findAll();
            if (salas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(salas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Visuaalizacion de sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaEscape> getSalaById(@PathVariable("id") Long id) {
        Optional<SalaEscape> salaData = salaRepository.findById(id);
        return salaData.map(sala -> new ResponseEntity<>(sala, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Para generar y crear nuevas salas
    @PostMapping
    public ResponseEntity<SalaEscape> createSala(@RequestBody SalaEscape sala) {
        try {
            SalaEscape nuevaSala = salaRepository.save(sala);
            return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminacion de salas
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSala(@PathVariable("id") Long id) {
        try {
            salaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar salas por dificultad
    @GetMapping("/dificultad/{dificultad}")
    public ResponseEntity<List<SalaEscape>> getSalasByDificultad(@PathVariable String dificultad) {
        try {
            List<SalaEscape> salas = salaRepository.findByDificultad(dificultad);
            if (salas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(salas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Seguir agregando busquedas y modificaciones sobre las salas
        }
    }

