package com.example.springdemopharmacystore.model.dictionaries;

import lombok.Getter;

@Getter
public enum DrugType {

    LIQUID("жидкость"),
    SOLID("твердая форма"),
    SOFT("мази, кремы"),
    AEROSOL("аэрозоль"),
    OTHER("другие");

    private final String russian;

    DrugType(String russian) {
        this.russian = russian;
    }
}
