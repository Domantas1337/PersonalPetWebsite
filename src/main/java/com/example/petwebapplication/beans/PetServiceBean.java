package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.apache.ibatis.exceptions.PersistenceException;
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


    @PersistenceContext
    private transient EntityManager entityManager;


    @Inject
    private transient PetRepository petRepository;

    @Inject
    private transient PetServiceRecordRepository petServiceRecordRepository; // Mark non-serializable fields as transient


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

    @Transactional
    public void updatePetServiceRecord(PetServiceRecord record) throws OptimisticLockException{
        try {
            PetServiceRecord existingRecord = petServiceRecordRepository.findById(record.getId()).orElse(null);;

            if (existingRecord == null) {
                throw new IllegalArgumentException("Record not found");
            }

            if (!existingRecord.getVersion().equals(record.getVersion())) {
                throw new OptimisticLockException("Attempted to update stale data");
            }

            existingRecord.setServiceName(record.getServiceName());
            existingRecord.setCost(record.getCost());
            existingRecord.setDetails(record.getDetails());
            existingRecord.setProviderName(record.getProviderName());
            existingRecord.setServiceDate(record.getServiceDate());
            petServiceRecordRepository.update(existingRecord);
        } catch (PersistenceException e) {
            logger.error("Error updating record", e);
            throw e;
        }
    }


    public String navigateToAddPetServiceRecord() {
        return "addPetServiceRecordPage?faces-redirect=true&petId=" + petId;
    }

    public String navigateFromAddPetServiceRecordsToServiceRecords(Long id){
        return "personalPetServicesPage?faces-redirect=true&petId=" + id;
    }
    public String navigateToAddVetVisit(){
        System.out.println(petId);
        return "addVetVisitPage?faces-redirect=true&petId=" + petId;
    }
    public void deletePetServiceById(Long recordId){
        petServiceRecordRepository.delete(recordId);
        loadServiceRecordsById();
    }

    public void loadServiceRecordsById(){
        petServiceRecordsForOnePet = petServiceRecordRepository.selectPetServiceRecordsByPetId(petId);
    }

    @Transactional
    public void addPetServiceRecord(){
        PetServiceRecord petServiceRecord = new PetServiceRecord();

        Pet petForPetServiceRecord = petRepository.findById(petId).orElse(null);

        petServiceRecord.setPet(petForPetServiceRecord);

        if(this.serviceName.isEmpty()){
            statusMessage = "Name" + VALUEISREQUIRED;
            return;
        }
         else if(this.providerName.isEmpty()) {
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
        petServiceRecord.setVersion(1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            petServiceRecord.setServiceDate(sdf.parse(this.serviceDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        petServiceRecordRepository.create(petServiceRecord);
        loadServiceRecordsById();
    }

}
