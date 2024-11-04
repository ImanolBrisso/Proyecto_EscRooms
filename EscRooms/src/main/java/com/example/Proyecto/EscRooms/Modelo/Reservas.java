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

    // Constructores
    public Reservas() {
    }

    public Reservas(SalaEscape salaEscape, LocalDateTime fechaReserva, String clienteEmail) {
        this.salaEscape = salaEscape;
        this.fechaReserva = fechaReserva;
        this.clienteEmail = clienteEmail;
    }

    // Parametros de getters y seters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalaEscape getSalaEscape() {
        return salaEscape;
    }

    public void setSalaEscape(SalaEscape salaEscape) {
        this.salaEscape = salaEscape;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    // Se puede sobreeescribr toString
    @Override
    public String toString() {
        return "Reservas{" +
                "id=" + id +
                ", salaEscape=" + salaEscape.getNombre() +
                ", fechaReserva=" + fechaReserva +
                ", clienteEmail='" + clienteEmail + '\'' +
                '}';
    }
}


