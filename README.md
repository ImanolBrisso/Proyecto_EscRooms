# 🔐 Proyecto de Gestión de Escape Rooms

 Este Proyecto se basa es un Sistema de gestiones de salas de escape donde la aplicación permitirá gestionar salas de escape y realizar las reservas correspondientes, teniendo como base la implementación de un modelo y o arquitectura MVC utilizando el framework de Spring (Spring Boot y Spring JPA) en conjunto con la base de datos en MySql.

## 🤖 Tecnologías Utilizadas

- **Backend:** Spring Boot  
- **Base de datos:** MySQL  
- **ORM (Object-Relational Mapping):** Spring JPA  
- **Frontend:** HTML, CSS  
- **Estructura:** Maven  

## 📁 Estructura del Proyecto - Guia

java/
└── com.example.Proyecto.EscRooms/
    ├── controller/
    │   ├── ReservasController
    │   └── SalaController
    ├── Modelo/
    │   ├── Reservas
    │   └── SalaEscape
    ├── repository/
    │   ├── ReservasRepository
    │   └── SalaEscapeRepository
    ├── service/
    │   └── ReservasService
    └── EscRoomsApplication.java

resources/
├── static/
│   └── css/
│       └── style.css
├── templates/
│   ├── crear_sala.html
│   ├── index.html
│   ├── lista_de_salas.html
│   └── reservar_sala.html
└── application.properties


## ⭐ Funcionalidades Generales

- **Gestión de Salas**
  - Crear, modificar y eliminar salas.
  - Ver listado de salas disponibles.

- **Sistema de Reservas**
  - Crear, gestionar y cancelar reservas.
  - Consultar calendario de reservas.

- **Funciones de Administración**
  - Gestión de usuarios y reportes.

## 📋 Endpoints Principales (API Endpoints)

- GET /salas - Listar salas
- POST /salas/crear - Crear sala
- GET /reservas - Ver reservas
- POST /reservas/nueva - Crear reserva
- DELETE /reservas/{id} - Cancelar reserva

## Colaborador

Imanol Brissolese
