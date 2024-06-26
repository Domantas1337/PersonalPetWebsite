package com.example.petwebapplication.entities.serviceRecords;

import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.enums.VetVisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class VetVisit {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "reason_for_visit", nullable = false, length = 1000)
    private String reasonForVisit;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "visit_type", nullable = false)
    private VetVisitType visitType;

    @Column(name = "veterinarian_name", nullable = false)
    private String veterinarianName;

    @Column(name = "diagnosis", nullable = true, length = 1000)
    private String diagnosis;

    @Column(name = "notes_on_diagnosis", nullable = true, length = 1000)
    private String notesOnDiagnosis;

    @Column(name = "care_recommendations", nullable = true, length = 1000)
    private String careRecommendations;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "next_scheduled_visit", nullable = true)
    private LocalDate nextScheduledVisit;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "next_scheduled_visit_type", nullable = true)
    private VetVisitType nextScheduledVisitType;
}
