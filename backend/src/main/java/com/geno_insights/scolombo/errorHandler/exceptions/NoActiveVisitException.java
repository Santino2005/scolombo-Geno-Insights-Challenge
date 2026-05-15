package com.geno_insights.scolombo.errorHandler.exceptions;

public class NoActiveVisitException extends RuntimeException {
    public NoActiveVisitException() {
        super("No active visit found for this DNI");
    }
}