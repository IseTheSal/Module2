package com.epam.esm.validator;

public abstract class EntityValidator {

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
