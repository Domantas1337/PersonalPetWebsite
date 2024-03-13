package com.example.petwebapplication.beans;

import com.example.petwebapplication.mappers.PetServiceRecordMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Named
@SessionScoped
public class PetServiceBean implements Serializable {

    @Inject
    PetServiceRecordMapper petServiceRecordMapper;

    private Long petId;
    private String serviceName;
    private Date serviceDate;
    private String providerName;
    private String details;
    private Double cost;

    @PostConstruct
    public void init() {
        String petIdParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");
        if (petIdParameter != null && !petIdParameter.isEmpty()) {
            petId = Long.parseLong(petIdParameter);
        }
    }

    public String navigateToAddPetServiceRecord() {
        // Instead of setting a field, pass the ID as a parameter
        return "addPetServiceRecordPage?faces-redirect=true";
    }

}
