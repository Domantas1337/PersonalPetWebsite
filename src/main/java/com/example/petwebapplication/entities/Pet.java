package com.example.petwebapplication.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "age", nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<PetServiceRecord> petServiceRecords;
}
