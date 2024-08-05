package com.example.springdemopharmacystore.service;

import com.example.springdemopharmacystore.model.DrugAddInput;

public interface DrugService {

    void addDrugToWarehouse(DrugAddInput input);
}
