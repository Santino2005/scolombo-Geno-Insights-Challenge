package com.geno_insights.scolombo.errorHandler.exceptions;

public class VisitExportException extends RuntimeException {
    public VisitExportException(Throwable cause) {
        super("Could not export visit history", cause);
    }
}
