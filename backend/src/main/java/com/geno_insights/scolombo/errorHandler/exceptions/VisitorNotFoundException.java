package com.geno_insights.scolombo.errorHandler.exceptions;

public class VisitorNotFoundException extends RuntimeException {
    public VisitorNotFoundException() {
        super("Visitor not found");
    }
}