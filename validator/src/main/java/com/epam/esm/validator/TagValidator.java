package com.epam.esm.validator;

public class TagValidator {

    private static final String NAME_REGEX = "^[A-zА-я]{3,30}$";

    public static boolean isNameValid(String name) {
        return ((name != null) && (name.matches(NAME_REGEX)));
    }
}
