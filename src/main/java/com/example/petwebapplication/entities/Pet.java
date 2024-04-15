package com.example.petwebapplication.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Valid
public class Pet {
    private Long id;

    @NotBlank(message = "Pet name is required")
    private String petName;
    private String imageURL;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;    private List<PetServiceRecord> petServiceRecords;
}
