package org.entityc.tutorial.exception;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
