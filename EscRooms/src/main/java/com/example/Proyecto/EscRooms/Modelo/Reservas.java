package com.example.Proyecto.EscRooms.Modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sala_escape_id", nullable = false)
    private SalaEscape salaEscape;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "cliente_email", nullable = false)
    private String clienteEmail;

    // Se crean los siguientes metodos para la clase Reservas Service de "nuevareserva..."

    public void setSalaEscape(SalaEscape salaEscape) {
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
    }

    public void setClienteEmail(String clienteEmail) {
    }

    // Creados los siguientes parametros: getters, setters, equals, hashCode y toString
}

