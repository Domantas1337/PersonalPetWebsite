package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
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

    @PersistenceContext
    private transient EntityManager entityManager; // Mark non-serializable fields as transient

    @Inject
    private transient PetRepository petRepository;

    @Inject
    private transient PetServiceRecordRepository petServiceRecordRepository; // Mark non-serializable fields as transient


    private List<Pet> pets;
    private Pet selectedPet;
    private String name;
    private String imageURL;
    private Integer age;
    private List<PetServiceRecord> petServiceRecords;
    private int petServiceRecordNumber;

    private static final String BASE_URI = "http://localhost:8080/petWebApplication-1.0-SNAPSHOT/api/pets"; // Update with your actual endpoint
    private Client client;
    private WebTarget target;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URI);
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
            petRepository.create(pet);
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

    @Transactional
    public String updatePet(Long id) {
        Pet currentPet = petRepository.findById(id).orElse(null);

        if (currentPet == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Pet not found"));
            return "Error - Pet not found";
        }

        boolean isUpdated = false;

        if (name != null && !name.isEmpty() && !name.equals(currentPet.getPetName())) {
            currentPet.setPetName(name);
            isUpdated = true;
        }

        if (age != null && age >= 0 && !age.equals(currentPet.getAge())) {
            currentPet.setAge(age);
            isUpdated = true;
        }

        if (imageURL != null && !imageURL.equals(currentPet.getImageURL())) {
            currentPet.setImageURL(imageURL);
            isUpdated = true;
        }

        if (isUpdated) {
            try {
                validatePet(currentPet);
                petRepository.update(currentPet);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Pet updated successfully"));
                loadPets();  // Refresh the list after update
                return "Success";
            } catch (ConstraintViolationException e) {
                for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    FacesContext.getCurrentInstance().addMessage(propertyPath, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }
                return "Error - Validation failed";
            } catch (Exception e) {
                logger.error("Error updating pet: {}", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
                return "Error - System error";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No changes to update"));
            return "Nothing updated"; // No changes were made
        }
    }


    public void loadPets() {
        try {
            // Make a GET request to fetch pets
            pets = target.request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Pet>>() {});

        } catch (Exception e) {
            logger.error("Error loading pets: {}", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load pets"));
        }
        //pets = petRepository.findAll();
    }

    public void deletePet(Long id) {
        petRepository.delete(id);
        loadPets();
    }
}
