package com.example.petwebapplication.entities;

import lombok.Data;

import java.util.List;

@Data
public class Pet {
    private Long id;
    private String petName;
    private String imageURL;
    private int age;
    private List<PetServiceRecord> petServiceRecords;
}
