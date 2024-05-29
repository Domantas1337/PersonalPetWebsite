package com.example.petwebapplication.services;

import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.faces.view.facelets.FaceletException;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

import javax.enterprise.context.ApplicationScoped;
import java.io.Console;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class PetServiceImpl implements PetService, Serializable {

    @Inject
    private PetRepository petRepository;
    @Inject
    private PetServiceRecordRepository petServiceRecordRepository;

    @Override
    public void addPetServiceRecord(PetServiceRecord record, Long petId) {
        record.setPet(petRepository.findById(petId).get());
        petServiceRecordRepository.create(record);
    }

    @Transactional
    public String updatePetServiceRecord(PetServiceRecord record) {
        try {
            PetServiceRecord existingRecord = petServiceRecordRepository.findById(record.getId()).get();
            existingRecord.updateFrom(record);
            String result = petServiceRecordRepository.update(record);

            return result;
        } catch (Exception ex) {
            System.out.println("General exceptions caught in service method: " + ex.getMessage());
            return "Error";
        }
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
