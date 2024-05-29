package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.entities.serviceRecords.VetVisit;
import com.example.petwebapplication.services.FileProcessingService;
import com.example.petwebapplication.services.ImageProcessingService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.Part;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@SessionScoped
@Named
@Data
public class FileUploadBean implements Serializable {

    @Inject
    ImageProcessingService imageProcessingService;

    private CompletableFuture<Object> recordProcessingTask = null;

    private Part uploadedFile;
    public String uploadFile() {
        try (InputStream input = uploadedFile.getInputStream()) {
            System.out.println("File uploaded!");
            BufferedImage bufferedImage = ImageIO.read(input);
            if (bufferedImage != null) {
                recordProcessingTask = CompletableFuture.supplyAsync(() -> imageProcessingService.processDocument(bufferedImage));
            } else {
                throw new RuntimeException("Failed to decode the uploaded image");
            }
        } catch (NullPointerException e) {
            System.out.println("Probably null");
        } catch (IOException e) {
            System.out.println("Something with input/output");
        }

        String petId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("petId");
        Long petIdLong = Long.parseLong(petId);
        System.out.println(petId);
        return "addPetServiceRecordPage?faces-redirect=true&petId=" + petIdLong;
    }

    public boolean isTheImageProcessing() {
        return recordProcessingTask != null && !recordProcessingTask.isDone();
    }

    public String getImageProcessingStatus() throws ExecutionException, InterruptedException {
        System.out.println(recordProcessingTask);

        if (recordProcessingTask == null) {
            return null;
        } else if (isTheImageProcessing()) {
            return "The file is still processing";
        }

        if(recordProcessingTask.get() instanceof PetServiceRecord) {
            PetServiceRecord processedRecord = (PetServiceRecord) recordProcessingTask.get();
        }

        return "Your image has been scanned";
    }


}

