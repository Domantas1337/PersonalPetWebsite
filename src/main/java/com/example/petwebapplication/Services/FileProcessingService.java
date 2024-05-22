package com.example.petwebapplication.Services;

import com.example.petwebapplication.enums.VetVisitType;
import jakarta.annotation.Resource;
import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Local;
import jakarta.ejb.SessionContext;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import com.example.petwebapplication.entities.serviceRecords.VetVisit;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;

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

