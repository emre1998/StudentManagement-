package com.App.StudentManagement.ErrorHandler;

    public class InvalidCredentialsException extends RuntimeException {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

