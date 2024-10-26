package com.example.Proyecto.EscRooms;

import com.example.Proyecto.EscRooms.Modelo.Reservas;
import com.example.Proyecto.EscRooms.Modelo.SalaEscape;

import java.util.Scanner;

/*
Por ahora unicamente se realiza la interaccion del usuario mediante esta clase
 */
public class Main {
    public static void main(String[] args) {
        Reservas sistema = new Reservas();
        Scanner scanner = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\n-------- Sistema de Reservas para Salas de Escape -------- ");
            System.out.println("1. Añadir nueva sala");
            System.out.println("2. Reservar una sala");
            System.out.println("3. Ver salas disponibles");
            System.out.println("4. Ver disponibilidad de fechas de una sala");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("-------- Introduce el nombre de la sala:--------  ");
                    String nombreSala = scanner.nextLine();
                    System.out.print("-------- Introduce la capacidad de la sala:--------  ");
                    int capacidad = Integer.parseInt(scanner.nextLine());
                    sistema.agregarSala(nombreSala, capacidad);
                    break;

                case "2":
                    System.out.print("-------- Introduce el nombre de la sala que deseas reservar:--------  ");
                    nombreSala = scanner.nextLine();
                    System.out.print("-------- Introduce la fecha para la reserva (dd-MM-yyyy):--------  "); // se comprueba de manera correcta la tipifacion de las fechas
                    String fecha = scanner.nextLine();
                    sistema.reservarSala(nombreSala, fecha);
                    break;

                case "3":
                    sistema.mostrarSalas();
                    break;

                case "4":
                    System.out.print("-------- Introduce el nombre de la sala:--------  ");
                    nombreSala = scanner.nextLine();
                    SalaEscape sala = sistema.buscarSala(nombreSala);
                    if (sala != null) {
                        sala.mostrarDisponibilidad();
                    }
                    break;

                case "5":
                    System.out.println("-------- Saliendo del sistema de reservas... -------- ");
                    break;

                default:
                    System.out.println("-------- Opción no válida. Inténtalo de nuevo. -------- ");
            }

        } while (!opcion.equals("5"));

        scanner.close();
    }
}
