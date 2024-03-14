package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.mappers.PetMapper;
import com.example.petwebapplication.mappers.PetServiceRecordMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Named
@SessionScoped
public class PetServiceBean implements Serializable {

    @Inject
    PetServiceRecordMapper petServiceRecordMapper;

    @Inject
    PetMapper petMapper;

    private Long petId;
    private String serviceName;
    private String serviceDate;
    private String providerName;
    private String details;
    private Double cost;
    private List<PetServiceRecord> petServiceRecordsForOnePet;

    @PostConstruct
    public void init() {
        String petIdParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");
        if (petIdParameter != null && !petIdParameter.isEmpty()) {
            petId = Long.parseLong(petIdParameter);
        }
    }

    public String navigateToAddPetServiceRecord() {
        // Instead of setting a field, pass the ID as a parameter
        return "addPetServiceRecordPage";
    }

    public void loadServiceRecordsById(){
        petServiceRecordsForOnePet = petMapper.selectPetServiceRecordsForPet(petId);
    }

    @Transactional
    public void addPetServiceRecord(){
        PetServiceRecord petServiceRecord = new PetServiceRecord();

        petServiceRecord.setPet(petMapper.findPetById(petId));
        petServiceRecord.setServiceName(this.serviceName);
        petServiceRecord.setCost(this.cost);
        petServiceRecord.setDetails(this.details);
        petServiceRecord.setProviderName(this.providerName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            petServiceRecord.setServiceDate(sdf.parse(this.serviceDate));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
        petServiceRecordMapper.insertRecord(petServiceRecord);

    }

}
