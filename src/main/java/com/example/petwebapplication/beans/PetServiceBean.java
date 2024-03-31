package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.mappers.PetMapper;
import com.example.petwebapplication.mappers.PetServiceRecordMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Named
@ViewScoped
public class PetServiceBean implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(PetServiceBean.class);

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
        return "addPetServiceRecordPage?faces-redirect=true&petId=" + petId;
    }

    public String navigateFromAddPetServiceRecordsToServiceRecords(Long id){
        return "personalPetServicesPage?faces-redirect=true&petId=" + id;
    }

    public void deletePetServiceById(Long recordId){
        petServiceRecordMapper.deleteRecordById(recordId);
        loadServiceRecordsById();
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
        loadServiceRecordsById();
    }

}
