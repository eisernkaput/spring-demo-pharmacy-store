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
@RequestMapping("/api/v1/drug")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping("/addDrug")
    public ResponseEntity<HttpStatus> addDrug(@RequestBody DrugAddInput Body) {
        try {
            drugService.addDrug(Body);
            return ResponseEntity.status(HttpStatus.OK).build();
            // добавить возврат номера шкафа и адреса склада
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/getDrug")
    public String getDrug() {
        try {
            return ResponseEntity.status(HttpStatus.OK).toString();
            // добавить возврат номера шкафа и адреса склада
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).toString();
        }
    }
}
