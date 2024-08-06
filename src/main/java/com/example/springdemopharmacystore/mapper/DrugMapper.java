package com.example.springdemopharmacystore.mapper;

import com.example.springdemopharmacystore.model.entity.Drug;
import com.example.springdemopharmacystore.rest.model.DrugDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrugMapper {

    @Mapping(target = "inStock", source = "isInStock")
    DrugDto toDrugDto(Drug drugEntity);

    List<DrugDto> toDrugDtos(List<Drug> drugEntities);
}
