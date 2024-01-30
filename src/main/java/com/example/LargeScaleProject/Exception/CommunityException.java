package com.example.LargeScaleProject.Exception;



public class CommunityException extends RuntimeException {
    public CommunityException(String message) {
        super(message);
    }

    public CommunityException(String message, Throwable cause) {
        super(message, cause);
    }
}
