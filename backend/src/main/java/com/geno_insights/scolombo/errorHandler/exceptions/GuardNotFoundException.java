package com.geno_insights.scolombo.errorHandler.exceptions;

public class GuardNotFoundException extends RuntimeException {
    public GuardNotFoundException() {
        super("Guard not found");
    }
}