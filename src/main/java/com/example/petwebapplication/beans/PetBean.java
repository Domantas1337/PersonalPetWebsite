package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.mappers.PetMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import java.util.List;

@Data
@Named
@RequestScoped
public class PetBean {
    @Inject
    private PetMapper petMapper;

    private List<Pet> pets;
    private Pet selectedPet;

    @PostConstruct
    public void init() {
        // Load pets when bean is created
        System.out.println("neee");
        loadPets();
    }

    public void loadPets() {
        // Use the mapper to get all pets from the database
        pets = petMapper.findAll();
        System.out.println(pets.size());
    }

    public void deletePet(Long id) {
        // Use the mapper to delete a pet by its ID
        petMapper.deletePetById(id);
        // Reload the list to reflect the changes
        loadPets();
    }
}
