package com.match.management.application;

public class InvalidGameResultException extends RuntimeException {

    public InvalidGameResultException(String message) {
        super(message);
    }
}
