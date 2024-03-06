package com.example.petwebapplication.mappers;
package com.example.petwebapplication.mappers;

import com.example.petwebapplication.entities.Pet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface PetServiceRecordMapper {
    List<PetServiceRecordMapper> findAll();

    PetServiceRecordMapper findById(@Param("id") Long id);

    void deletePetById(@Param("id") Long id);

    void updatePet(PetServiceRecordMapper petServiceRecordMapper);
}
