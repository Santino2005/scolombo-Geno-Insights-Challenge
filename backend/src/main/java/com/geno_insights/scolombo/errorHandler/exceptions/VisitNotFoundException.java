package com.geno_insights.scolombo.errorHandler.exceptions;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException() {
        super("Visit not found");
    }
}