package com.example.LargeScaleProject.Exception;

public class MovieException extends RuntimeException {
    public MovieException(String message) {
        super(message);
    }

    public MovieException(String message, Throwable cause) {
        super(message, cause);
    }
}
