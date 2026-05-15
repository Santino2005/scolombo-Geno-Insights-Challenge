package com.geno_insights.scolombo.errorHandler.exceptions;

public class VisitorAlreadyInsideException extends RuntimeException {
    public VisitorAlreadyInsideException() {
        super("Visitor already inside");
    }
}