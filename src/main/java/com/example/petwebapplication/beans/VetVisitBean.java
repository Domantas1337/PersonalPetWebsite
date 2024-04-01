package com.example.petwebapplication.beans;

import com.example.petwebapplication.enums.VetVisitType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Data;

import java.util.Date;

@Data
@Named
@RequestScoped
public class VetVisitBean {
    private String reasonForVisit;
    private Date visitDate;
    private VetVisitType visitType;
    private String veterinarianName;
    private String diagnosis;
    private String notesOnDiagnosis;
    private String careRecommendations;
    private float cost;
    private Date nextScheduledVisit;
    private VetVisitType nextScheduledVisitType;
}
