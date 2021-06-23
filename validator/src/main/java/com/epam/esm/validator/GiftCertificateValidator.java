package com.epam.esm.validator;

import java.math.BigDecimal;

public class GiftCertificateValidator extends EntityValidator {

    private static final String NAME_REGEX = "^[A-zА-яёЁ]{3,30}$";
    private static final String DESCRIPTION_REGEX = "^(([A-zА-я0-9.,?!;:]+\\s)*[A-zА-я0-9.,?!;:]+){10,300}$";
    private static final String MAX_PRICE = "1000.0";

    public static boolean isNameValid(String name) {
        return ((name != null) && name.matches(NAME_REGEX));
    }

    public static boolean isDescriptionValid(String description) {
        return ((description != null) && description.matches(DESCRIPTION_REGEX));
    }

    public static boolean isPriceValid(BigDecimal price) {
        return (((price != null) && (price.compareTo(BigDecimal.ZERO) == 1)
                && price.compareTo(new BigDecimal(MAX_PRICE)) == -1));
    }

    public static boolean isDurationValid(Integer duration) {
        return (duration != null && duration > 0);
    }
}
