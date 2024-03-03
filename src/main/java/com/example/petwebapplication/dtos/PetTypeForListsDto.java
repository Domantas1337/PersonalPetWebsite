package com.example.petwebapplication.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
public class PetTypeForListsDto {

    @EqualsAndHashCode.Include
    private Long id;
    private String typeName;

    public PetTypeForListsDto() {
    }
    public PetTypeForListsDto(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }
}
