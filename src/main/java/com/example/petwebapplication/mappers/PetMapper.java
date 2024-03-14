package com.example.petwebapplication.mappers;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {

    List<PetServiceRecord> selectPetServiceRecordsForPet(@Param("id") Long id);
    List<Pet> findAll();
    Pet findPetById(@Param("id") Long id);
    void deletePetById(@Param("id") Long id);
    void insertPet(Pet pet);
    void updatePet(Pet pet);
}
