package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetType;

import com.example.petwebapplication.repositories.PetTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Named
@RequestScoped
public class PetTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PetTypeRepository petTypeRepository;

    private String typeName;
    private String generalCareInfo;
    private String imageURL;

    private List<PetType> petTypes;

    // Default constructor
    public PetTypeBean() {
    }

    public String navigateToPetTypes() {
        loadPetTypes(); // Load data before navigation
        return "viewPetTypes"; // Navigate to petTypesPage.xhtml
    }
    private void loadPetTypes() {
        petTypes = petTypeRepository.findAll(); // Method to fetch pet types from DB
    }
    @PostConstruct
    public void init() {
        petTypes = new ArrayList<>();
    }

    public void findPets() {
        petTypes = entityManager.createQuery("SELECT p FROM PetType p", PetType.class).getResultList();
    }


    // Method to save a pet type to the database
    @Transactional
    public void savePetType() {

        PetType petType = new PetType();
        petType.setTypeName(this.typeName);
        petType.setGeneralCareInfo(this.generalCareInfo);
        petType.setImageUrl(this.imageURL);

        entityManager.persist(petType);
    }
}

