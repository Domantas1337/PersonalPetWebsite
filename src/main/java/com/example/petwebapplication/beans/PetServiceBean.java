package com.example.petwebapplication.beans;

import com.example.petwebapplication.converters.DateConverter;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ValidationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.example.petwebapplication.converters.DateConverter.stringToDate;

@Data
@Named
@ViewScoped
public class PetServiceBean implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(PetServiceBean.class);

    @Inject
    private PetService petService;

    private PetServiceRecord petServiceRecord;
    private List<PetServiceRecord> petServiceRecords;

    private Long petId;

    private String serviceDate;
    private String nextVisitDate;

    private boolean editMode = false;

    @PostConstruct
    public void init() {
        petServiceRecord = new PetServiceRecord();
        String petIdParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");
        if (petIdParameter != null && !petIdParameter.isEmpty()) {
            petId = Long.parseLong(petIdParameter);
        }
        loadServiceRecordsById();
    }

    public void addPetServiceRecord() throws ParseException {

        petServiceRecord.setServiceDate(DateConverter.stringToDate(serviceDate, "yyyy-MM-dd"));

        try {
            petService.addPetServiceRecord(petServiceRecord, petId);
        } catch (ValidationException e) {
            // Handle user feedback
        }
    }


    public void deletePetServiceRecord(Long id) {
        petService.deletePetServiceRecord(id);
    }

    public void loadServiceRecords() {
        petServiceRecords = petService.getPetServiceRecordsByPetId(petServiceRecord.getPet().getId());
    }

    public void loadServiceRecordsById(){
        petServiceRecords = petService.getPetServiceRecordsByPetId(petId);
    }

    public String navigateToAddPetServiceRecord() {
        return "addPetServiceRecordPage?faces-redirect=true&petId=" + petId;
    }

    public String navigateToUpdateServiceRecord(Long recordId) {
        return "updatePetServiceRecordPage?faces-redirect=true&petServiceRecordId=" + recordId;
    }

    public String navigateToAddVetVisit(){
        System.out.println(petId);
        return "addVetVisitPage?faces-redirect=true&petId=" + petId;
    }


}
