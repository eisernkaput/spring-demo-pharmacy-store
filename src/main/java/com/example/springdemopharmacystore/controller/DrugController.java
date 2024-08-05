package com.example.springdemopharmacystore.controller;

import com.example.springdemopharmacystore.model.DrugAddInput;
import com.example.springdemopharmacystore.service.DrugService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Drug controller")
@RestController
@RequestMapping("/v1/drug")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping("/add_drug_to_warehouse")
    public ResponseEntity<HttpStatus> addDrugToWarehouse(@RequestBody DrugAddInput Body) {
        try {
            drugService.addDrugToWarehouse(Body);
            return ResponseEntity.status(HttpStatus.OK).build();
            // добавить возврат номера шкафа и адреса склада
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/get_drug_from_warehouse")
    public String getDrugFromWarehouse() {
        try {
            return ResponseEntity.status(HttpStatus.OK).toString();
            // добавить возврат номера шкафа и адреса склада
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).toString();
        }
    }
}
