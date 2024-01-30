package com.example.LargeScaleProject.Exception;

public class PostException extends RuntimeException {
    public PostException(String errorInCreatingAPost, Exception e) {
        super(errorInCreatingAPost,e);
    }
}
