package com.geno_insights.scolombo.errorHandler.exceptions;

public class VisitAlreadyClosedException extends RuntimeException {
    public VisitAlreadyClosedException() {
        super("Visit already closed");
    }
}