package com.example.petwebapplication.beans;

import com.example.petwebapplication.dtos.PetTypeForListsDto;
import com.example.petwebapplication.entities.*;
import com.example.petwebapplication.mappers.PetMapper;
import com.example.petwebapplication.repositories.PetTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Named
@RequestScoped
public class PetBean {

}
