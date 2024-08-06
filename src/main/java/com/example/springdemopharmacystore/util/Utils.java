package com.example.springdemopharmacystore.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utils {

    public static <T extends Enum<T>, E extends Enum<E>> T getEnum(Enum<E> inputEnum, Class<T> outputClass) {
        if (inputEnum == null) {
            return null;
        }
        try {
            return Enum.valueOf(outputClass, inputEnum.name());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EnumConstantNotPresentException(outputClass, inputEnum.name());
        }
    }
}
