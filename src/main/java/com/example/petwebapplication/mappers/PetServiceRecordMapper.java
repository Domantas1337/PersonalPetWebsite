package com.example.petwebapplication.mappers;

import com.example.petwebapplication.entities.Pet;
import com.example.petwebapplication.entities.PetServiceRecord;
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
    void deleteRecordById(@Param("id") Long id);
    void updateRecord(PetServiceRecordMapper petServiceRecordMapper);
    void insertRecord(PetServiceRecord petServiceRecord);
}
