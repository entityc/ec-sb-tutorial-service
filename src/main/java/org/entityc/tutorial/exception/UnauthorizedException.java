package org.entityc.tutorial.exception;


public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
