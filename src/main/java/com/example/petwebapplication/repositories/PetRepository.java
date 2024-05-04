package com.example.petwebapplication.repositories;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
public class PetRepository implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public Pet create(Pet pet) {
        entityManager.persist(pet);

        return pet;
    }

    public List<Pet> findAll() {
        return entityManager.createQuery("SELECT p FROM Pet p", Pet.class).getResultList();
    }

    public Optional<Pet> findById(Long id) {
        Pet pet = entityManager.find(Pet.class, id);
        return Optional.ofNullable(pet);
    }

    public void delete(Long id) {

        var pet = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet Id:" + id));

        entityManager.remove(pet);
    }

    public Pet update(Pet pet) {
        return entityManager.merge(pet);
    }
}
