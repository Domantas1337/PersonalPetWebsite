/*
package beans;

import com.example.petwebapplication.beans.PetServiceBean;
import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.repositories.PetRepository;
import com.example.petwebapplication.repositories.PetServiceRecordRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import java.util.Optional;
public class PetServiceBeanTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private PetServiceRecordRepository petServiceRecordRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PetServiceBean petServiceBean;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOptimisticLockException() {
        // Given
        Long recordId = 1L;
        PetServiceRecord existingRecord = new PetServiceRecord();
        existingRecord.setId(recordId);
        existingRecord.setVersion(1);

        PetServiceRecord recordToUpdate = new PetServiceRecord();
        recordToUpdate.setId(recordId);
        recordToUpdate.setVersion(2); // Simulating version mismatch

        when(petServiceRecordRepository.findById(recordId)).thenReturn(Optional.of(existingRecord));
        when(entityManager.merge(any(PetServiceRecord.class))).thenReturn(existingRecord);

        try {
            // When
            petServiceBean.updatePetServiceRecord(recordToUpdate);
            Assertions.fail("Expected an OptimisticLockException to be thrown");
        } catch (OptimisticLockException e) {
            // Then
            System.out.println("Caught an OptimisticLockException: " + e.getMessage());
            Assertions.assertEquals("Attempted to update stale data", e.getMessage());
        }

        verify(entityManager, never()).merge(any(PetServiceRecord.class)); // Ensuring merge is not called due to the exception
    }
}
*/
