package com.devsh.itracker.service.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException() {
        super("User not found.");
    }
}
