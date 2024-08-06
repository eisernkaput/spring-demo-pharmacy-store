package com.example.springdemopharmacystore.controller;

import com.example.springdemopharmacystore.rest.api.DrugStoreWarehouseApi;
import com.example.springdemopharmacystore.rest.model.AddDrugRequestDto;
import com.example.springdemopharmacystore.rest.model.AddDrugResponseDto;
import com.example.springdemopharmacystore.rest.model.GetDrugRequestDto;
import com.example.springdemopharmacystore.rest.model.GetDrugResponseDto;
import com.example.springdemopharmacystore.service.DrugService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Drug controller")
@RestController
@RequestMapping("/v1/drug")
@RequiredArgsConstructor
public class DrugController implements DrugStoreWarehouseApi {

    private final DrugService drugService;

    @Override
    @PostMapping("/add_drug_to_warehouse")
    public ResponseEntity<AddDrugResponseDto> addDrugToWarehouse(@RequestBody AddDrugRequestDto addDrugRequestDto) {
        return ResponseEntity.ok(drugService.addDrugToWarehouse(addDrugRequestDto));
    }

    @Override
    @PostMapping("/get_drug_from_warehouse")
    public ResponseEntity<GetDrugResponseDto> getDrugFromWarehouse(@RequestBody GetDrugRequestDto getDrugRequestDto) {
        return ResponseEntity.ok(drugService.getDrugFromWarehouse(getDrugRequestDto));
    }
}
