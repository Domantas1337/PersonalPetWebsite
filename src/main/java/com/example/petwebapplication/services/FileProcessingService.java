package com.example.petwebapplication.services;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import jakarta.enterprise.inject.Specializes;

import javax.enterprise.context.ApplicationScoped;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@ApplicationScoped
@Specializes
public class FileProcessingService extends ImageProcessingService implements Serializable {

    @Override
    public Object processDocument(BufferedImage bufferedImage){

        System.out.println("Record Processing");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){

        }
        System.out.println("Finished processing record");

        LocalDate todaysDate = LocalDate.now();
        PetServiceRecord serviceRecord = new PetServiceRecord(){

            {
                setPet(new Pet());
                setCost(3.14);
                setServiceDate(new Date());
                setServiceName("Name");
                setDetails("Details");
            }
        };

        return serviceRecord;
    }


}

