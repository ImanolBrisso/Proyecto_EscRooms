package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*; //javax reemplazado por jakarta - librerias
import java.time.LocalDateTime;

@Entity
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clienteEmail;
    private LocalDateTime fechaReserva;

    @ManyToOne
    @JoinColumn(name = "sala_escape_id")
    private SalaEscape salaEscape;

    // Constructor vacío para JPA
    public Reservas() {}

    public Reservas(String clienteEmail, LocalDateTime fechaReserva, SalaEscape salaEscape) {
        this.clienteEmail = clienteEmail;
        this.fechaReserva = fechaReserva;
        this.salaEscape = salaEscape;
    }

    // Parametros de getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public SalaEscape getSalaEscape() {
        return salaEscape;
    }

    public void setSalaEscape(SalaEscape salaEscape) {
        this.salaEscape = salaEscape;
    }
}


