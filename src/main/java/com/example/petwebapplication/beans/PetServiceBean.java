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

import static com.example.petwebapplication.constants.InputFields.VALUEISREQUIRED;

@Data
@Named
@ViewScoped
public class PetServiceBean implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(PetServiceBean.class);
    private String statusMessage = "";


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
        if (petIdParameter != null && !petIdParameter.isEmpty()) {
            petId = Long.parseLong(petIdParameter);
        }
        loadServiceRecordsById();
    }

    public String navigateToAddPetServiceRecord() {
        return "addPetServiceRecordPage?faces-redirect=true&petId=" + petId;
    }

    public String navigateFromAddPetServiceRecordsToServiceRecords(Long id){
        return "personalPetServicesPage?faces-redirect=true&petId=" + id;
    }
    public String navigateToAddVetVisit(Long id){
        return "addVetVisitPage?faces-redirect=true&petId=" + id;
    }
    public void deletePetServiceById(Long recordId){
        petServiceRecordMapper.deleteRecordById(recordId);
        loadServiceRecordsById();
    }

    public void loadServiceRecordsById(){
        petServiceRecordsForOnePet = petServiceRecordMapper.selectPetServiceRecordsByPetId(petId);
    }

    @Transactional
    public void addPetServiceRecord(){
        PetServiceRecord petServiceRecord = new PetServiceRecord();

        Pet petForPetServiceRecord = petMapper.selectPetById(petId);
        petServiceRecord.setPet(petForPetServiceRecord);

        if(this.serviceName.isEmpty()){
            statusMessage = "Name" + VALUEISREQUIRED;
            return;
        } else if(this.serviceName.isEmpty()){
            statusMessage = "Service name" + VALUEISREQUIRED;
            return;
        } else if(this.providerName.isEmpty()) {
            statusMessage = "Provider name" + VALUEISREQUIRED;
            return;
        } else if(this.serviceDate.isEmpty()) {
            statusMessage = "Service date" + VALUEISREQUIRED;
            return;
        }

        petServiceRecord.setServiceName(this.serviceName);
        petServiceRecord.setCost(this.cost);
        petServiceRecord.setDetails(this.details);
        petServiceRecord.setProviderName(this.providerName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            petServiceRecord.setServiceDate(sdf.parse(this.serviceDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        petServiceRecordMapper.insertRecord(petServiceRecord);
        loadServiceRecordsById();
    }

}
