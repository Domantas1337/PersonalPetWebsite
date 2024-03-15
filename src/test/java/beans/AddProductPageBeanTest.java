package beans;
import com.example.petwebapplication.beans.AddProductPageBean;
import com.example.petwebapplication.entities.PetType;
import com.example.petwebapplication.entities.Product;
import com.example.petwebapplication.repositories.PetTypeRepository;
import com.example.petwebapplication.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AddProductPageBeanTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private AddProductPageBean addProductPageBean;

    @BeforeEach
    public void setup() {
        // Initialize the selectedPetTypeIds list with valid mock IDs
        List<Long> petTypeIds = Arrays.asList(1L, 2L, 3L); // Example IDs
        addProductPageBean.setSelectedPetTypeIds(petTypeIds);

        // Mock the findById method to return a PetType for each ID
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(new PetType(/* set properties as needed */)));
        when(petTypeRepository.findById(2L)).thenReturn(Optional.of(new PetType(/* set properties as needed */)));
        when(petTypeRepository.findById(3L)).thenReturn(Optional.of(new PetType(/* set properties as needed */)));

        // ... other setup ...
    }
    @Test
    public void testSaveProductErrorMessage() {
        // Setup
        when(productRepository.create(any(Product.class))).thenThrow(new RuntimeException("Test Exception"));

        // Act
        String result = addProductPageBean.saveProduct();

        // Assert
        // Verify that the method returns the expected error indicator, such as null or an error page.
        assertEquals("Error", result);

        // Additional verification to ensure the error was logged could be added if you mock the logger
    }

}
