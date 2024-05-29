package com.example.petwebapplication.beans;

import com.example.petwebapplication.services.FileProcessingService;
import com.example.petwebapplication.entities.serviceRecords.VetVisit;
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

@Named
@Data
@SessionScoped
public class FileUploadBean implements Serializable {

    @Inject
    FileProcessingService fileProcessingService;

    private CompletableFuture<VetVisit> vetVisitProcessingTask = null;

    private Part uploadedFile;
    public String uploadFile() {
        try (InputStream input = uploadedFile.getInputStream()) {
            System.out.println("File uploaded!");
            BufferedImage bufferedImage = ImageIO.read(input);
            if (bufferedImage != null) {
                vetVisitProcessingTask = CompletableFuture.supplyAsync(() -> fileProcessingService.processScannedDocument(bufferedImage));
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
        return vetVisitProcessingTask != null && !vetVisitProcessingTask.isDone();
    }

    public String getImageProcessingStatus() throws ExecutionException, InterruptedException {
        System.out.println(vetVisitProcessingTask);

        if (vetVisitProcessingTask == null) {
            return null;
        } else if (isTheImageProcessing()) {
            return "The file is still processing";
        }

        VetVisit provessedVetVisit = vetVisitProcessingTask.get();
        return "Your image has been scanned";
    }


}

