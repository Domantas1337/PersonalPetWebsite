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
import jakarta.transaction.SystemException;
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
    private Date serviceDateNotString;
    private String providerName;
    private String details;
    private Double cost;
    private List<PetServiceRecord> petServiceRecordsForOnePet;

    @PostConstruct
    public void init() {
        String petIdParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");
        System.out.println("petIdParameter " + petIdParameter);
        if (petIdParameter != null && !petIdParameter.isEmpty()) {
            petId = Long.parseLong(petIdParameter);
        }
        loadServiceRecordsById();
    }

    public String navigateToAddPetServiceRecord() {
        // Instead of setting a field, pass the ID as a parameter
        return "addPetServiceRecordPage";
    }

    public void loadServiceRecordsById(){
        System.out.println("petId " + petId);
        petServiceRecordsForOnePet = petServiceRecordMapper.selectPetServiceRecordsByPetId(petId);
        System.out.println("petServiceRecordsForOnePet" + petServiceRecordsForOnePet);
    }

    @Transactional
    public void addPetServiceRecord(){
        PetServiceRecord petServiceRecord = new PetServiceRecord();

        Pet petForPetServiceRecord = petMapper.selectPetById(petId);
        petServiceRecord.setPet(petForPetServiceRecord);

        petServiceRecord.setServiceName(this.serviceName);
        System.out.println(this.cost);
        petServiceRecord.setCost(this.cost);
        System.out.println(this.details);
        petServiceRecord.setDetails(this.details);
        System.out.println(this.providerName);
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
