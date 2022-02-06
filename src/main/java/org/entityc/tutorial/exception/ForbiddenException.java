package org.entityc.tutorial.exception;


public class ForbiddenException extends ServiceException {

    public ForbiddenException(String msg) {
        super(msg);
    }
}
