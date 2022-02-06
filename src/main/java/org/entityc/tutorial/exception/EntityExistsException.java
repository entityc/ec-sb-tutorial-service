package org.entityc.tutorial.exception;

public class EntityExistsException extends ServiceException {

    public EntityExistsException(String message) {
        super(message);
    }
}
