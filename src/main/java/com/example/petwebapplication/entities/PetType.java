package com.example.petwebapplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity
@Data // Generates getters, setters, equals(), hashCode(), and toString() methods.
@NoArgsConstructor // Generates a no-args constructor.
@AllArgsConstructor
public class PetType {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "type_name", nullable = false)
    private String typeName;

    @Column(name = "general_care_info", nullable = false)
    private String generalCareInfo;

    @ManyToMany(mappedBy = "petTypes", fetch = FetchType.EAGER) // Referring to the 'petTypes' field in Product
    private List<Product> products;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;
}
