package com.juanite.util;

public class Validator {
    public static boolean validateUsername(String username){
        return username.matches("^[a-zA-Z0-9]{3,15}$");
    }
    public static boolean validatePassword(String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,15}$");
    }
    public static boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
