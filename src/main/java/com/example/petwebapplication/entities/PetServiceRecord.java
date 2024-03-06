package com.example.petwebapplication.entities;

import lombok.Data;

import java.util.Date;

@Data
public class PetServiceRecord {
    private Long id; // Unique identifier for the service record
    private Pet pet;
    private String serviceType; // Type of service provided (e.g., grooming, vet visit)
    private Date serviceDate; // Date when the service was provided
    private String providerName; // Name of the service provider
    private String details; // Additional details or notes about the service
    private Double cost; // The cost of the service

}
