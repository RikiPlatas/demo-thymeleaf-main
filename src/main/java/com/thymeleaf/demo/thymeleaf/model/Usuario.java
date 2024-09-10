package com.thymeleaf.demo.thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Usuario")
public class Usuario extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick")
        private String nick;

        @Column(name = "password")
        private String password;

        @Column(name = "nombre")
        private String nombre;

        @Column(name = "apellido1")
        private String apellido1;

        @Column(name = "apellido2")
        private String apellido2;

        @Column(name = "telefono")
        private String telefono;

        @Column(name = "email")
        private String email;

    }
