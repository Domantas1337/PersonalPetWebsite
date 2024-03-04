package com.example.petwebapplication.services;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.petwebapplication.mappers.PetMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@RequestScoped
public class PetService {

    @Inject
    @Mapper
    private PetMapper petMapper;

    // Autowire the PetMapper using constructor injection (recommended by Spring)

    public PetService(){

    }

    public PetService(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    public List<Pet> findAllPets() {
        return petMapper.findAll();
    }

    public Pet findPetById(Long id) {
        return petMapper.findById(id);
    }

    public void deletePetById(Long id) {
        petMapper.deletePetById(id);
    }

    public void updatePet(Pet pet) {
        petMapper.updatePet(pet);
    }
}
