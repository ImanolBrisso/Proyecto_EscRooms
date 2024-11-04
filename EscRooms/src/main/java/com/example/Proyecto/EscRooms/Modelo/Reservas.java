package com.example.Proyecto.EscRooms.Modelo;

import com.example.Proyecto.EscRooms.Modelo.SalaEscape;
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

    // Metodos creados de ReservasService
    public void setSalaEscape(SalaEscape salaEscape) {
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
    }

    public void setClienteEmail(String clienteEmail) {
    }

    // Creados los siguientes parametros getters y setters
}


