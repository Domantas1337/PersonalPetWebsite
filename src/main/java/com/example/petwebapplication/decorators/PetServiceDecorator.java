package com.example.petwebapplication.decorators;

import com.example.petwebapplication.entities.PetServiceRecord;
import com.example.petwebapplication.interfaces.PetService;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.xml.bind.ValidationException;
import org.apache.ibatis.exceptions.PersistenceException;

import java.io.Console;
import java.util.Date;

@Decorator
public abstract class PetServiceDecorator implements PetService {
    @Inject
    @Delegate
    private PetService petService;

    @Override
    public void addPetServiceRecord(PetServiceRecord record) {
        additionalValidate(record);
        petService.addPetServiceRecord(record);
    }

    private void additionalValidate(PetServiceRecord record) {
        if (record.getCost() != null && record.getCost() < 0) {
            throw new IllegalArgumentException("Cost cannot be negative.");
        }

        if (record.getServiceName() == null || record.getServiceName().isEmpty()) {
            throw new IllegalArgumentException("Service name is required.");
        }
    }
}