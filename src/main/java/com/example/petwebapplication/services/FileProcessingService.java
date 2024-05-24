package com.example.petwebapplication.services;

import com.example.petwebapplication.enums.VetVisitType;
import com.example.petwebapplication.entities.serviceRecords.VetVisit;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

@ApplicationScoped
public class FileProcessingService {

    public VetVisit processScannedDocument(BufferedImage bufferedImage){

        System.out.println("Processing");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){

        }
        System.out.println("Finished processing");

        LocalDate todaysDate = LocalDate.now();
        VetVisit scannedVetVisit = new VetVisit(){

            {
                setReasonForVisit("Processed reason");
                setVisitDate(todaysDate);
                setVisitType(VetVisitType.CHECKUP);
                setVeterinarianName("Processed name");
                setDiagnosis("Processed diagnosis");
                setNotesOnDiagnosis("Processed notes");
                setCareRecommendations("Processed care recommendations");
                setCost(3.14);
                setNextScheduledVisit(todaysDate.plusDays(10));
                setNextScheduledVisitType(VetVisitType.VACCINATION);
            }
        };

        return scannedVetVisit;
    }


}

