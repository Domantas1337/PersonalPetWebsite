package com.example.petwebapplication.services;

import jakarta.ejb.Asynchronous;
import jakarta.enterprise.inject.Default;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.awt.image.BufferedImage;
import java.io.Serializable;


@ApplicationScoped
public class ImageProcessingService implements Serializable{
    public Object processDocument(BufferedImage bufferedImage) {
        System.out.println("Image Processing");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){

        }
        System.out.println("Finished processing image");
        return null; // Placeholder return object
    }
}
