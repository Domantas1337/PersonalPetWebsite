package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.constants.*;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.mappers.PetMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static com.example.petwebapplication.constants.InputFields.MORETHANZERO;
import static com.example.petwebapplication.constants.InputFields.VALUEISREQUIRED;

@Data
@Named
@RequestScoped
public class PetBean {
    private final Logger logger = LoggerFactory.getLogger(PetBean.class);

    private String statusMessage = "";

    @Inject
    private PetMapper petMapper;

    private List<Pet> pets;
    private Pet selectedPet;
    private String name;
    private String imageURL;
    private Integer age;

    @PostConstruct
    public void init() {
        loadPets();
    }

    public String navigateToPersonalPetServiceRecords(Long petId) {
        return "personalPetServicesPage?faces-redirect=true&petId=" + petId;
    }

    private void validatePet(Pet pet) {
        Set<ConstraintViolation<Pet>> violations = jakarta.validation.Validation.buildDefaultValidatorFactory()
                .getValidator().validate(pet);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
    @Transactional
    public String addPet() {
        try {
            Pet pet = new Pet();

            if(this.name.isEmpty()){
                statusMessage = "Name" + VALUEISREQUIRED;
                return "Nothing";
            }

            pet.setPetName(this.name);

            if(this.age < 0){
                statusMessage = "Age" + MORETHANZERO;
                return "Nothing";
            }

            pet.setAge(this.age);
            pet.setImageURL(this.imageURL);

            validatePet(pet);
            petMapper.insertPet(pet);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Pet added successfully"));
            return "Success";
        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                FacesContext.getCurrentInstance().addMessage(propertyPath, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
            return "Error - Validation failed";
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return "Error - System error";
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
