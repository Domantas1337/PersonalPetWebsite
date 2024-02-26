package com.example.petwebapplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, equals(), hashCode(), and toString() methods.
@NoArgsConstructor // Generates a no-args constructor.
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne(optional = false)
    private PetType petType;


}
