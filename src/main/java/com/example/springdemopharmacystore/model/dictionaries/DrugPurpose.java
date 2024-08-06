package com.example.springdemopharmacystore.model.dictionaries;

import lombok.Getter;

@Getter
public enum DrugPurpose {

    ANTISEPTICS("антисептик"),
    ANTIMICROBIAL("антимикробный препарат"),
    ANTIBIOTICS("антибиотик"),
    ANALGESICS("обезбаливающее"),
    ANTIALLERGIC("антигистаминное"),
    ANTIPYRETIC("жаропонижающее"),
    NOOTROPIC("ноотропный препарат"),
    CONTRACEPTIVES("контрацептивы");

    private final String russian;

    DrugPurpose(String russian) {
        this.russian = russian;
    }

}
