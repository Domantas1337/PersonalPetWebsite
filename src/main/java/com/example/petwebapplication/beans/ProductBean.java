package com.example.petwebapplication.beans;

import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ProductRepository productRepository;

    private List<Product> products;
    private String productName;
    private BigDecimal price;
    private String description;
    private Set<PetType> petTypes;

    public String navigateToPetTypes() {
        loadProducts(); // Load data before navigation
        return "viewPetTypes"; // Navigate to petTypesPage.xhtml
    }
    private void loadProducts() {
        products = productRepository.findAll(); // Method to fetch pet types from DB
    }
    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public void findProducts() {
        products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
    @Transactional
    public void savePetType() {

        Product product = new Product();
        product.setProductName(this.productName);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setPetTypes(this.petTypes);

        productRepository.create(product);
    }
}
