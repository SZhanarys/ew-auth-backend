package com.example.ewauthbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // В Postgres создастся таблица users
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}