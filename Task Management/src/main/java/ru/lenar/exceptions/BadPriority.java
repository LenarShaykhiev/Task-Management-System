package ru.lenar.exceptions;

public class BadPriority extends RuntimeException{
    public BadPriority(String message) {
        super(message);
    }
}
