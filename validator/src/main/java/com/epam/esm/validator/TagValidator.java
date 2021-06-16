package com.epam.esm.validator;

public class TagValidator extends EntityValidator {

    private static final String NAME_REGEX = "^[A-zА-яёЁ]{3,30}$";

    public static boolean isNameValid(String name) {
        return ((name != null) && (name.matches(NAME_REGEX)));
    }

}
