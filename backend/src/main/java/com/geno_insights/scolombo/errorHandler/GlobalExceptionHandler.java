package com.geno_insights.scolombo.errorHandler;

import com.geno_insights.scolombo.errorHandler.exceptions.ActiveVisitAlreadyExistsException;
import com.geno_insights.scolombo.errorHandler.exceptions.GuardAlreadyExistsException;
import com.geno_insights.scolombo.errorHandler.exceptions.GuardNotFoundException;
import com.geno_insights.scolombo.errorHandler.exceptions.InvalidPinException;
import com.geno_insights.scolombo.errorHandler.exceptions.NoActiveVisitException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitAlreadyClosedException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitExportException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitNotFoundException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitorAlreadyInsideException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<ApiError> handleInvalidPin(InvalidPinException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(GuardNotFoundException.class)
    public ResponseEntity<ApiError> handleGuardNotFound(GuardNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Internal server error"));
    }
    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ApiError> handleVisitNotFound(VisitNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(NoActiveVisitException.class)
    public ResponseEntity<ApiError> handleNoActiveVisit(NoActiveVisitException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(VisitorAlreadyInsideException.class)
    public ResponseEntity<ApiError> handleVisitorAlreadyInside(
            VisitorAlreadyInsideException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(VisitAlreadyClosedException.class)
    public ResponseEntity<ApiError> handleVisitAlreadyClosed(
            VisitAlreadyClosedException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(VisitExportException.class)
    public ResponseEntity<ApiError> handleVisitExport(VisitExportException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ex.getMessage()));
    }
    @ExceptionHandler(VisitorNotFoundException.class)
    public ResponseEntity<ApiError> handleVisitorNotFound(
            VisitorNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage()));
    }
    @ExceptionHandler(ActiveVisitAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleActiveVisit(
            ActiveVisitAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage()));
    }
    @ExceptionHandler(GuardAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleGuardAlreadyExists(
            GuardAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage()));
    }
}