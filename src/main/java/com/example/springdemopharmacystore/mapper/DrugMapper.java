package com.example.springdemopharmacystore.mapper;

import com.example.springdemopharmacystore.entity.Drug;
import com.example.springdemopharmacystore.model.DrugAddInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DrugMapper {

    @Mapping(source="drugName", target="name")
    Drug toEntity(DrugAddInput input);
}
