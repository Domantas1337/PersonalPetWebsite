package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.io.Serializable;

@Data
@Named
@ViewScoped
public class PetServiceUpdateBean implements Serializable {

    @Inject
    private PetService petService;

    private Long petId;
    private PetServiceRecord currentPetServiceRecord;
    private String message;

    @PostConstruct
    public void init() {
        message = "";
        String petServiceRecordParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petServiceRecordId");
        String navigationPetId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");

        if (petServiceRecordParameter != null && !petServiceRecordParameter.isEmpty()) {
            Long petServiceId = Long.parseLong(petServiceRecordParameter);
            Long navigationPetIdLong = Long.parseLong(navigationPetId);

            currentPetServiceRecord = petService.findPetServiceRecordById(petServiceId);
            petId = navigationPetIdLong;
        }
    }

    public String updatePetServiceRecord() {
        try {
            PetServiceRecord newestRecord = petService.findPetServiceRecordById(currentPetServiceRecord.getId());
            String result = petService.updatePetServiceRecord(currentPetServiceRecord);

            System.out.println("This is the result");
            System.out.println(result);

            if ("Success".equals(result)) {
                message = "Update successful!";
                return "personalPetServicesPage?faces-redirect=true&petId=" + petId;
            } else if ("Lock".equals(result)) {
                message = "Update failed due to optimistic lock!";
            } else {
                message = "An error occurred during the update!";
            }

        } catch (OptimisticLockException e) {
            System.out.println("Optimistic");
        } catch (Exception e){
            return "personalPetServicesPage?faces-redirect=true&petId=" + petId;
        }

        return null;
    }


    public String goBackToTheServicePage() {
        return "personalPetServicesPage?faces-redirect=true&petId=" + petId;
    }
}
