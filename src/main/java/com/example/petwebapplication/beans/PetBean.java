package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.mappers.PetMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
@Data
@Named
@RequestScoped
public class PetBean {
    private final Logger logger = LoggerFactory.getLogger(PetBean.class);

    @Inject
    private PetMapper petMapper;

    private List<Pet> pets;
    private Pet selectedPet;
    private String name;
    private String imageURL;
    private int age;

    @PostConstruct
    public void init() {
        loadPets();
    }

    public String navigateToPersonalPetServiceRecords(Long petId) {
        return "personalPetServicesPage?faces-redirect=true&petId=" + petId;
    }

    @Transactional
    public String addPet(){
        try {
            Pet pet = new Pet();
            pet.setPetName(this.name);
            pet.setAge(this.age);
            pet.setImageURL(this.imageURL);

            petMapper.insertPet(pet);
            return "Success";
        }catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            return "Error";
        }
    }


    public void loadPets() {
        pets = petMapper.findAll();
    }

    public void deletePet(Long id) {
        petMapper.deletePetById(id);
        loadPets();
    }
}
