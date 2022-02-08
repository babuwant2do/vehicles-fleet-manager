package com.wordpress.babuwant2do.vehiclesfleetmanager.exception;

import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.errors.ErrorConstants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
public class ApplicationCustomException extends RuntimeException{
    @Setter(AccessLevel.PRIVATE)
    private HttpStatus status;

    @Setter(AccessLevel.PRIVATE)
    private String description;

    public ApplicationCustomException(String message) {
        this(message, HttpStatus.EXPECTATION_FAILED, ErrorConstants.ERR_INTERNAL_SERVER_ERROR);
    }

    public ApplicationCustomException(String message, String description) {
        this(message, HttpStatus.EXPECTATION_FAILED, description);
    }

    public ApplicationCustomException(String message, HttpStatus status, String description) {
        super(message);
        this.status = status;
        this.description = description;
    }
}
