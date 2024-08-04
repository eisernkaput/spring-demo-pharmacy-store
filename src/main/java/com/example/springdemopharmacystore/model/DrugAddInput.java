package com.example.springdemopharmacystore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugAddInput {

    private String warehouseId;

    private String drugName;

    private String drugManufacturer;
}
