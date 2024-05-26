package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import lombok.Data;

import java.io.Serializable;

@Data
@Named
@ViewScoped
public class PetServiceUpdateBean implements Serializable {

    @Inject
    private PetService petService;

    private PetServiceRecord currentPetServiceRecord;

    @PostConstruct
    public void init() {
        System.out.println("its in");

        String petServiceRecordParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petServiceRecordId");
        if (petServiceRecordParameter != null && !petServiceRecordParameter.isEmpty()) {
            Long petServiceId = Long.parseLong(petServiceRecordParameter);
            currentPetServiceRecord = petService.findPetServiceRecordById(petServiceId);
        }
        System.out.println("Name");
        System.out.println(currentPetServiceRecord.getServiceName());
    }

    public void updatePetServiceRecord() {
        try {
            petService.updatePetServiceRecord(currentPetServiceRecord);
        } catch (OptimisticLockException e) {
        }
    }

}
