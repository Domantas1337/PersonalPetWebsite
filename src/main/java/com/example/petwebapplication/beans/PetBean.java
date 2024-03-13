package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.mappers.PetMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
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
    private String name;
    private String imageURL;
    private int age;

    @PostConstruct
    public void init() {
        // Load pets when bean is created
        loadPets();
    }

    public String navigateToPersonalPetServiceRecords(Long petId) {
        return "personalPetServicesPage?faces-redirect=true&productId=" + petId;
    }

    @Transactional
    public void addPet(){
        Pet pet = new Pet();
        pet.setPetName(this.name);
        pet.setAge(this.age);
        pet.setImageURL(this.imageURL);

        System.out.println(pet.getPetName());
        petMapper.insertPet(pet);

        var petss = petMapper.findAll();
        for (Pet pett : petss) {
            System.out.println(pett.getPetName());
        }
    }

    public void loadPets() {
        pets = petMapper.findAll();
    }

    public void deletePet(Long id) {
        petMapper.deletePetById(id);
    }
}
