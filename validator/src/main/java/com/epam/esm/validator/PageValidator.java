package com.epam.esm.validator;

public class PageValidator {

    public static boolean isPaginationValid(int page, int amount) {
        return ((page > 0) && (amount >= 0));
    }
}