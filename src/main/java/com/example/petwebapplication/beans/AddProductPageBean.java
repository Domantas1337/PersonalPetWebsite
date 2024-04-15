package com.example.petwebapplication.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.petwebapplication.dtos.PetTypeForListsDto;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.petwebapplication.constants.InputFields.MORETHANZERO;
import static com.example.petwebapplication.constants.InputFields.VALUEISREQUIRED;

@Data
@Named
@RequestScoped
public class AddProductPageBean {
    private final Logger logger = LoggerFactory.getLogger(AddProductPageBean.class);
    private String statusMessage = "";

    @Inject
    private PetTypeRepository petTypeRepository;

    @Inject
    private ProductRepository productRepository;

    private String productName;
    private BigDecimal price;
    private String description;
    private String shortDescription;
    private List<Long> selectedPetTypeIds;
    private List<PetTypeForListsDto> petTypeDTOs;

    public AddProductPageBean() {
    }

    @PostConstruct
    public void init() {
        loadPetTypes();
    }

    private void loadPetTypes() {
        List<PetType> petTypeList = petTypeRepository.findAll();
        petTypeDTOs = petTypeList.stream()
                .map(petType -> new PetTypeForListsDto(petType.getId(), petType.getTypeName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public String saveProduct() {
        try {

            Product product = new Product();

            if(this.productName.isEmpty()){
                statusMessage = "Name" + VALUEISREQUIRED;
                return "Nothing";
            } else if (this.price.intValue() < 0) {
                statusMessage = "Price" + MORETHANZERO;
                return "Nothing";
            }

            product.setProductName(this.productName);
            product.setPrice(this.price);
            product.setDescription(this.description);
            product.setShortDescription(this.shortDescription);

            List<PetType> selectedPetTypes = selectedPetTypeIds.stream()
                    .map(id -> petTypeRepository.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            product.setPetTypes(selectedPetTypes);

            productRepository.create(product);
            return "Success";
        }catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            return "Error";
        }
    }
}
