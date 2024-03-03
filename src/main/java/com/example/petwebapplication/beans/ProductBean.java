package com.example.petwebapplication.beans;

import com.example.petwebapplication.dtos.PetTypeForProductDto;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import java.util.logging.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;

@Data
@Named
@ViewScoped
public class ProductBean implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID

    @PersistenceContext
    private transient EntityManager entityManager; // Mark non-serializable fields as transient


    @Inject
    private transient PetTypeRepository petTypeRepository;

    // Ensure all other fields are Serializable. Basic types and collections of serializable types are fine.

    private List<PetTypeForProductDto> petTypeDTOs; // Ensure PetTypeForProductDto is Serializable

    @Inject
    private transient ProductRepository productRepository;

    private List<Product> products; // Ensure Product is Serializable
    private String productName;
    private BigDecimal price;
    private String description;
    private String short_description;

    private List<PetType> productPetTypes; // Ensure PetType is Serializable

    private Product selectedProduct;

    private List<Long> selectedPetTypeIds;
    public ProductBean() {
    }
    public String navigateToAddProducts() {
        return "addProductPage";
    }


    private void loadPetTypes() {
        List<PetType> petTypeList = petTypeRepository.findAll();
        petTypeDTOs = petTypeList.stream()
                .map(petType -> new PetTypeForProductDto(petType.getId(), petType.getTypeName()))
                .collect(Collectors.toList());
    }
    @PostConstruct
    public void init() {
        loadPetTypes();
        products = new ArrayList<>();
    }

    public String navigateToProductDetails(Long productId) {
        // Instead of setting a field, pass the ID as a parameter
        return "viewProductDetails?faces-redirect=true&productId=" + productId;
    }

    public void findProducts() {
        products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
    @Transactional
    public String saveProduct() {
        Product product = new Product();
        product.setProductName(this.productName);
        product.setPrice(this.price);
        product.setShortDescription(this.short_description);
        product.setDescription(this.description);


        List<PetType> selectedPetTypes = selectedPetTypeIds.stream()
                .map(id -> entityManager.find(PetType.class, id))
                .collect(Collectors.toList());
        product.setPetTypes(selectedPetTypes);

        productRepository.create(product);
        return "success"; // Navigate to success page or whatever outcome you want
    }
}
