package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.rest.model.AddDrugRequestDto;
import com.example.springdemopharmacystore.rest.model.AddDrugResponseDto;
import com.example.springdemopharmacystore.rest.model.GetDrugRequestDto;
import com.example.springdemopharmacystore.rest.model.GetDrugResponseDto;

public interface DrugService {

    AddDrugResponseDto addDrugToWarehouse(AddDrugRequestDto input);

    GetDrugResponseDto getDrugFromWarehouse(GetDrugRequestDto input);
}
