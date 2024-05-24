package com.example.petwebapplication.interfaces;

import com.example.petwebapplication.entities.PetServiceRecord;
import jakarta.persistence.OptimisticLockException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

public interface PetService {
    void addPetServiceRecord();
    void updatePetServiceRecord(PetServiceRecord record) throws OptimisticLockException, PersistenceException;
    void deletePetServiceById(Long recordId);
    void loadServiceRecordsById();
}
