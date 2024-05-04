package com.example.petwebapplication.beans;

import com.example.petwebapplication.dtos.PetTypeForListsDto;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Data
@Named
@ViewScoped
public class ProductBean implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private String statusMessage = "";

    @PersistenceContext
    private transient EntityManager entityManager;

    @Inject
    private transient PetTypeRepository petTypeRepository;

    private List<PetTypeForListsDto> petTypeDTOs;

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

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public String navigateToProductDetails(Long productId) {
        // Instead of setting a field, pass the ID as a parameter
        return "viewProductDetails?faces-redirect=true&productId=" + productId;
    }


}
