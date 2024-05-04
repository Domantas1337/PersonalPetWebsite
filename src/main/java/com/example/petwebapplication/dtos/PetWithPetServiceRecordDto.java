package com.example.petwebapplication.dtos;

import com.example.petwebapplication.entities.PetServiceRecord;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PetWithPetServiceRecordDto {

    private String petName;
    private String imageURL;
    private Integer age;
}
