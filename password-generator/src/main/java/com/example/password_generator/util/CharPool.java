package com.example.password_generator.util;

public class CharPool {
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITS = "0123456789";
    public static final String SYMBOLS = "!@#$%^&*()-_=+[]{};:,.<>/?";

    private CharPool() {}

    public static String buildPool(boolean upper, boolean lower, boolean digits, boolean symbols) {
        StringBuilder sb = new StringBuilder();
        if (upper) sb.append(UPPER);
        if (lower) sb.append(LOWER);
        if (digits) sb.append(DIGITS);
        if (symbols) sb.append(SYMBOLS);
        return sb.toString();
    }
}
