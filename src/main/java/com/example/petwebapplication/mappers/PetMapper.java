package com.example.petwebapplication.mappers;

import com.example.petwebapplication.entities.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {
    Pet selectPetById(@Param("id") Long id);

    List<Pet> selectAllPets();

    void insertPet(Pet pet);

    void updatePet(Pet pet);

    void deletePet(@Param("id") Long id);
}
