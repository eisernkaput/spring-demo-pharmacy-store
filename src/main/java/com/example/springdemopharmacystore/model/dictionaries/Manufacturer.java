package com.example.springdemopharmacystore.model.dictionaries;

import lombok.Getter;

@Getter
public enum Manufacturer {

    R_PHARM("Р-Фарм"),
    BIOCAD("Биокад"),
    GENERIUM("Генериум"),
    VALENTA_PHARM("Валента Фарм"),
    PHARMASYNTHEZ("Фармасинтез"),
    CANONPHARNA("Канонфарма Продакшн"),
    PHARMSTANDART_UFAVITA("Фармстандарт-Уфавита"),
    OZON("Озон"),
    VERTEX("Вертекс"),
    SOTEX("Сотекс");

    private final String russian;

    Manufacturer(String russian) {
        this.russian = russian;
    }
}
