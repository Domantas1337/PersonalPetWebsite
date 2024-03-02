package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import jakarta.transaction.Transactional;

@Data
@Named
@RequestScoped
public class PetBean {

    @PersistenceContext
    private EntityManager entityManager;

    private String petName;
    private int age;
    private Long petTypeId;

    public PetBean() {
    }

    // Method to save a user to the database
    @Transactional
    public void savePet() {
        Pet pet = new Pet();
        pet.setPetName(this.petName);
        pet.setAge(this.age);

        // Find the PetType by ID and set it
        PetType petType = entityManager.find(PetType.class, this.petTypeId);
        pet.setPetType(petType);

        entityManager.persist(pet);
    }
}
