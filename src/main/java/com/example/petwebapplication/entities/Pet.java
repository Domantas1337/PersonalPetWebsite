package com.example.petwebapplication.entities;

import lombok.Data;

@Data
public class Pet {
    private Long id;
    private String petName;
    private String imageURL;
    private int age;
}
