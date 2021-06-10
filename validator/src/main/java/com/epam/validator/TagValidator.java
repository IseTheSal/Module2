package com.epam.validator;

public class TagValidator {

    private static final String NAME_REGEX = "^[A-z|А-я]{3,30}$";

    public static boolean isNameValid(String name) {
        return name.matches(NAME_REGEX);
    }

    public static boolean isIdValid(String id) {
        boolean isValid;
        try {
            long idValue = Long.parseLong(id);
            isValid = (idValue > 0);
        } catch (NumberFormatException ex) {
            isValid = false;
        }
        return isValid;
    }
}
