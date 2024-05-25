package com.example.petwebapplication.interfaces;

import java.util.List;
import jakarta.ejb.Local;
import com.example.petwebapplication.entities.PetServiceRecord;

public interface PetService {
    void addPetServiceRecord(PetServiceRecord record, Long petId);
    void updatePetServiceRecord(PetServiceRecord record);
    void deletePetServiceRecord(Long recordId);
    List<PetServiceRecord> getPetServiceRecordsByPetId(Long petId);
    PetServiceRecord findPetServiceRecordById(Long recordId);
}
