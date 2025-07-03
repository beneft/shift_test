package org.example;

import java.util.regex.Pattern;

public class Qualifier {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+(e[-+]?\\d+)?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern FLOAT_PATTERN = Pattern.compile(
            "^-?(\\d+\\.\\d*|\\.\\d+)(e[-+]?\\d+)?$",
            Pattern.CASE_INSENSITIVE
    );

    public static boolean isInteger(String s) {
        return INTEGER_PATTERN.matcher(s).matches();
    }

    public static boolean isFloat(String s) {
        return FLOAT_PATTERN.matcher(s).matches();
    }
}
