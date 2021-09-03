package com.epam.esm.validator;

public class UserValidator {

    private static final String LOGIN_REGEX = "^[A-z]{8,30}$";
    private static final String PASSWORD_REGEX = "^[A-z0-9]{8,30}$";
    private static final String NAME_REGEX = "^[A-zА-я]{3,30}$";

    public static boolean isLoginValid(String login) {
        return ((login != null) && (login.matches(LOGIN_REGEX)));
    }

    public static boolean isPasswordValid(String password) {
        return ((password != null) && (password.matches(PASSWORD_REGEX)));
    }

    public static boolean isFirstNameValid(String name) {
        return ((name != null) && (name.matches(NAME_REGEX)));
    }

    public static boolean isLastNameValid(String name) {
        return ((name != null) && (name.matches(NAME_REGEX)));
    }
}
