package com.example.petwebapplication.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
public class PetTypeForProductDto {

    @EqualsAndHashCode.Include
    private Long id;
    private String typeName;

    public PetTypeForProductDto() {
    }
    public PetTypeForProductDto(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }
}
