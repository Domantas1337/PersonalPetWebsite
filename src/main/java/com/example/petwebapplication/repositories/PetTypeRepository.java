package com.example.petwebapplication.repositories;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Stateless
public class PetTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PetType create(PetType petType) {
        entityManager.persist(petType);

        return petType;
    }

    public List<PetType> findAll() {
        return entityManager.createQuery("SELECT c FROM PetType c", PetType.class).getResultList();
    }

    public Optional<PetType> findById(Long id) {
        PetType petType = entityManager.find(PetType.class, id);
        return Optional.ofNullable(petType);
    }

    public void delete(Long id) {

        var petType = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet type Id:" + id));

        entityManager.remove(petType);
    }

    public PetType update(PetType petType) {
        return entityManager.merge(petType);
    }
}
