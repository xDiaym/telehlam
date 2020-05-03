package org.juicecode.telehlam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("^" +             // Begin of string
                "(?=.*[a-z])" +                             // Lowercase letters
                "(?=.*[A-Z])" +                             // Uppercase letters
                "(?=.*[0-9])" +                             // Digits
                "(?=.*[!@#\\$%\\^&\\*\\(\\)\\-_=\\+])" +    // Symbols
                "(?=.{7,})"                                 // Minimal length
        );
        Matcher matcher = pattern.matcher(password.trim());
        return matcher.find();
    }

    public static boolean validateLogin(String login) {
        Pattern pattern = Pattern.compile("^" + // Beginning of string
                "([a-z0-9_]{4,32})" + // Lowercase symbols, digits and underscore. Length: [4-32]
                "$" // End of string
        );
        Matcher matcher = pattern.matcher(login.trim());
        return matcher.find();
    }

}
