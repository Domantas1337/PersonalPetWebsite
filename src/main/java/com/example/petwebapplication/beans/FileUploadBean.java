package com.example.petwebapplication.beans;

import com.example.petwebapplication.Services.FileProcessingService;
import com.example.petwebapplication.entities.serviceRecords.VetVisit;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.Part;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Named
@Data
@RequestScoped
public class FileUploadBean {

    @Inject
    FileProcessingService fileProcessingService;

    private CompletableFuture<VetVisit> vetVisitProcessingTask = null;

    private Part uploadFile;
    public void uploadFile() {
        try (InputStream input = uploadFile.getInputStream()) {

            BufferedImage bufferedImage = ImageIO.read(input);
            if (bufferedImage != null) {
                vetVisitProcessingTask = CompletableFuture.supplyAsync(() -> fileProcessingService.processScannedDocument(bufferedImage));
            } else {
                throw new RuntimeException("Failed to decode the uploaded image");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTheImageProcessing() {
        return vetVisitProcessingTask != null && !vetVisitProcessingTask.isDone();
    }

    public String getJerseyGenerationStatus() throws ExecutionException, InterruptedException {
        if (vetVisitProcessingTask == null) {
            return null;
        } else if (isTheImageProcessing()) {
            return "The file is still processing";
        }

        VetVisit provessedVetVisit = vetVisitProcessingTask.get();

        return "Your veterinary visit data has been uploaded";
    }
}

