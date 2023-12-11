package ru.lenar.exceptions;

public class BadStatus extends RuntimeException{
    public BadStatus(String message) {
        super(message);
    }
}
