package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static com.example.petwebapplication.constants.InputFields.MORETHANZERO;
import static com.example.petwebapplication.constants.InputFields.VALUEISREQUIRED;

@Data
@Named
@SessionScoped
public class PetBean implements Serializable {
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

    private Pet petForUpdate;
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

    public String navigateToUpdatePet(Long petId) {
        petForUpdate = pets.stream()
                .filter(pet -> petId.equals(pet.getId()))
                .findFirst().get();

        return "updatePetPage?faces-redirect=true";
    }

    @Transactional
    public String addPet() {
        try {
            Pet pet = new Pet();

            if (this.name.isEmpty()) {
                statusMessage = "Name" + VALUEISREQUIRED;
                return "Nothing";
            }

            pet.setPetName(this.name);

            if (this.age < 0) {
                statusMessage = "Age" + MORETHANZERO;
                return "Nothing";
            }

            pet.setAge(this.age);
            pet.setImageURL(this.imageURL);

            // Make a POST request to add the pet
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(pet, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Pet added successfully"));
                loadPets();  // Refresh the list after adding
                return "Success";
            } else {
                String errorMessage = response.readEntity(String.class);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorMessage));
                return "Error - Unable to add pet";
            }
        } catch (jakarta.validation.ConstraintViolationException e) {
            for (jakarta.validation.ConstraintViolation<?> violation : e.getConstraintViolations()) {
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

        if (id == null || petForUpdate == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid input"));
            return "Error - Invalid input";
        }

        if (!id.equals(petForUpdate.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Pet ID must match the path parameter"));
            return "Error - Pet ID must match the path parameter";
        }

        try {

            Response response = target.path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(petForUpdate, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Pet updated successfully"));
                return "Success";
            } else {
                String errorMessage = response.readEntity(String.class);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorMessage));
                return "Error - Unable to update pet";
            }
        }  catch (Exception e) {
            logger.error("Error updating pet: {}", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return "Error - System error";
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
        try {
            // Make a DELETE request to delete the pet
            Response response = target.path(String.valueOf(id))
                    .request()
                    .delete();

            if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Pet deleted successfully"));
                loadPets();
            } else {
                String errorMessage = response.readEntity(String.class);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorMessage));
            }

        } catch (Exception e) {
            logger.error("Error deleting pet: {}", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete pet"));
        }

    }
}
