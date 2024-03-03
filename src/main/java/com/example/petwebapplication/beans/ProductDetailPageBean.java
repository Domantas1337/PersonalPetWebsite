package com.example.petwebapplication.beans;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import lombok.Data;

@Data
@Named
@RequestScoped
public class ProductDetailPageBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product product; // Assume Product is a class in your model

    @Inject
    private transient ProductRepository productRepository; // Assume this is your JPA repository or similar service

    @PostConstruct
    public void init() {
        // Fetch the product ID from request parameter
        String productIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        if (productIdParam != null && !productIdParam.isEmpty()) {
            Long productId = Long.parseLong(productIdParam);
            product = productRepository.findById(productId).orElse(null); // Handle the case where the product is not found appropriately
        }
    }

    // Getter and setter for product (if not using Lombok @Data)
}
