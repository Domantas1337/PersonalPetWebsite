package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetType;

import com.example.petwebapplication.repositories.PetTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Data
@Named
@SessionScoped
public class PetTypeBean implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private static final transient Logger LOGGER = Logger.getLogger(ProductBean.class.getName());

    @PersistenceContext
    private transient EntityManager entityManager;

    @Inject
    private transient PetTypeRepository petTypeRepository;

    private String typeName;
    private String generalCareInfo;
    private String imageURL;

    private List<PetType> petTypes;

    private PetType selectedPetType;

    // Default constructor
    public PetTypeBean() {
    }

    public String navigateToPetTypes() {
        loadPetTypes(); // Load data before navigation
        return "viewPetTypes"; // Navigate to petTypesPage.xhtml
    }

    public String navigateToPetTypeDetails(Long typeId) {
        selectedPetType = petTypeRepository.findById(typeId).orElse(null);
        return "petTypeDetails?faces-redirect=true";
    }

    private void loadPetTypes() {
        petTypes = petTypeRepository.findAll();
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

