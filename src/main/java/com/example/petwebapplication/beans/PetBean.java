package com.example.petwebapplication.beans;

import com.example.petwebapplication.dtos.PetTypeForListsDto;
import com.example.petwebapplication.entities.*;
import com.example.petwebapplication.mappers.PetMapper;
import com.example.petwebapplication.repositories.PetTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Named
@RequestScoped
public class PetBean {

    @Inject
    private PetTypeRepository petTypeRepository;

    @Inject
    private PetMapper petMapper; // Assume injection is configured properly


    private List<PetTypeForListsDto> petTypeForListsDtoList;

    private String petName;
    private int age;
    private Long selectedPetType;

    public PetBean() {
    }
    @PostConstruct
    public void init() {
        loadPetTypes();
    }

    private void loadPetTypes() {
        List<PetType> petTypeList = petTypeRepository.findAll();
        petTypeForListsDtoList = petTypeList.stream()
                .map(petType -> new PetTypeForListsDto(petType.getId(), petType.getTypeName()))
                .collect(Collectors.toList());
    }

    public void savePet() {
        Pet pet = new Pet();
        pet.setPetName(petName);
        pet.setAge(age);
        pet.setPetType(
                petTypeRepository.findById(selectedPetType).get()
        );

        petMapper.insertPet(pet);
    }
    // Method to save a user to the database

}
