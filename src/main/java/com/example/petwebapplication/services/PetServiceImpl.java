package com.example.petwebapplication.services;

import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class PetServiceImpl implements PetService, Serializable {

    @Inject
    private PetRepository petRepository;
    @Inject
    private PetServiceRecordRepository petServiceRecordRepository;

    @Override
    public void addPetServiceRecord(PetServiceRecord record) {
        petServiceRecordRepository.create(record);
    }

    @Override
    public void updatePetServiceRecord(PetServiceRecord record) {
        PetServiceRecord existingRecord = petServiceRecordRepository.findById(record.getId()).get();

        existingRecord.updateFrom(record);
        petServiceRecordRepository.update(existingRecord);
    }

    @Transactional
    @Override
    public void deletePetServiceRecord(Long recordId) {
        PetServiceRecord record = petServiceRecordRepository.findById(recordId).get();
        petServiceRecordRepository.delete(recordId);
    }

    @Override
    public List<PetServiceRecord> getPetServiceRecordsByPetId(Long petId) {
        return petServiceRecordRepository.selectPetServiceRecordsByPetId(petId);
    }

    @Override
    public PetServiceRecord findPetServiceRecordById(Long recordId) {
        return petServiceRecordRepository.findById(recordId).get();
    }
}
