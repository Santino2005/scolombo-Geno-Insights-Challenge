package com.geno_insights.scolombo.errorHandler.exceptions;

public class ActiveVisitAlreadyExistsException extends RuntimeException {
    public ActiveVisitAlreadyExistsException() {
        super("Visitor already has an active credential");
    }
}