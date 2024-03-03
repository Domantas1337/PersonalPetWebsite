package com.example.petwebapplication.beans;

import com.example.petwebapplication.dtos.PetTypeForProductDto;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Named
@RequestScoped
public class AddProductPageBean {
    @Inject
    private PetTypeRepository petTypeRepository;

    @Inject
    private ProductRepository productRepository;

    private String productName;
    private BigDecimal price;
    private String description;
    private String shortDescription;
    private List<Long> selectedPetTypeIds;
    private List<PetTypeForProductDto> petTypeDTOs;

    public AddProductPageBean() {
    }

    @PostConstruct
    public void init() {
        loadPetTypes();
    }

    private void loadPetTypes() {
        List<PetType> petTypeList = petTypeRepository.findAll();
        petTypeDTOs = petTypeList.stream()
                .map(petType -> new PetTypeForProductDto(petType.getId(), petType.getTypeName()))
                .collect(Collectors.toList());
    }

    public String saveProduct() {
        Product product = new Product();
        product.setProductName(this.productName);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setShortDescription(this.shortDescription);

        List<PetType> selectedPetTypes = selectedPetTypeIds.stream()
                .map(id -> petTypeRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        product.setPetTypes(selectedPetTypes);

        productRepository.create(product); // Assuming the method to persist a product is named save
        return "successPage?faces-redirect=true"; // Navigate to a success page or listing page after saving
    }
}
