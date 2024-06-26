package com.example.petwebapplication.repositories;


import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.entities.PetType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
public class PetServiceRecordRepository implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public PetServiceRecord create(PetServiceRecord petServiceRecord) {
        entityManager.persist(petServiceRecord);

        return petServiceRecord;
    }

    public List<PetServiceRecord> findAll() {
        return entityManager.createQuery("SELECT c FROM PetServiceRecord c", PetServiceRecord.class).getResultList();
    }
    public List<PetServiceRecord> selectPetServiceRecordsByPetId(Long petId){
        return entityManager.createQuery("SELECT c FROM PetServiceRecord c  WHERE c.pet.id = :petId", PetServiceRecord.class)
                .setParameter("petId", petId)
                .getResultList();
    }
    public Optional<PetServiceRecord> findById(Long id) {
        PetServiceRecord petServiceRecord = entityManager.find(PetServiceRecord.class, id);
        return Optional.ofNullable(petServiceRecord);
    }

    public void delete(Long id) {

        var petServiceRecord = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet service record Id:" + id));

        entityManager.remove(petServiceRecord);
    }

    public String update(PetServiceRecord petServiceRecord) {
        try {
            entityManager.merge(petServiceRecord);
            return "Success";
        } catch (OptimisticLockException ex) {
            System.out.println("OptimisticLockException thrown in repository method");
            return "Lock";
        } catch (Exception ex) {
            System.out.println("Other exception caught in repository method: " + ex.getMessage());
            throw ex;  // rethrow other exceptions
        }
    }



}
