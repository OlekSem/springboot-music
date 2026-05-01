package com.example.springbootmusic.exception;

public class GenreAlreadyExistsException extends RuntimeException{
    public GenreAlreadyExistsException(String message) {
        super(message);
    }
}
