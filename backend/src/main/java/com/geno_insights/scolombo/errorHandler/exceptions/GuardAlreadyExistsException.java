package com.geno_insights.scolombo.errorHandler.exceptions;

public class GuardAlreadyExistsException extends RuntimeException {
    public GuardAlreadyExistsException() {
        super("Guard already exists");
    }
}