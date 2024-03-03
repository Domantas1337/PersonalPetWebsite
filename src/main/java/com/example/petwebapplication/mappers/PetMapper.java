package com.example.petwebapplication.mappers;

import com.example.petwebapplication.entities.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {
    @Select("SELECT * FROM PET")
    List<Pet> findAll();
    @Select("SELECT * FROM PET WHERE id = #{id}")
    Pet findById(@Param("id") Long id);
    @Delete("DELETE FROM PET where id = #{id}")
    void deletePetById(@Param(value = "id") int id);
    @Update("UPDATE PET SET pet_name = #{petName}, age = #{age}, image_url = {imaageURL} WHERE id = #{id}")
    void updatePet(Pet pet);
}
