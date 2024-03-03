package com.example.petwebapplication.entities;


import com.example.petwebapplication.entities.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

@Entity
@Data // Generates getters, setters, equals(), hashCode(), and toString() methods.
@NoArgsConstructor // Generates a no-args constructor.
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "short_description", nullable = true)
    private String shortDescription;

    @Lob
    @Column(name = "description", nullable = true)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "product_pet_types", // Name of the join table
            joinColumns = @JoinColumn(name = "product_id"), // Column for the join table corresponding to this entity
            inverseJoinColumns = @JoinColumn(name = "pet_type_id") // Column for the join table corresponding to the PetType entity
    )
    private List<PetType> petTypes;
}

