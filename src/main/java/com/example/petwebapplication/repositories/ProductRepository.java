package com.example.petwebapplication.repositories;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Stateless
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Product create(Product product) {
        entityManager.persist(product);

        return product;
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT c FROM Product c", Product.class).getResultList();
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    public void delete(Long id) {

        var product = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet type Id:" + id));

        entityManager.remove(product);
    }

    public Product update(Product product) {
        return entityManager.merge(product);
    }
}
