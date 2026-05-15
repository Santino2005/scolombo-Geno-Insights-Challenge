package com.geno_insights.scolombo.errorHandler.exceptions;

public class InvalidPinException extends RuntimeException {
    public InvalidPinException() {
        super("Invalid pin");
    }
}