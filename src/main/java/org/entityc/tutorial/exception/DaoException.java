package org.entityc.tutorial.exception;

public class DaoException extends ServiceException {

    public DaoException(String msg, Throwable e) {
        super(msg, e);
    }

    public DaoException(String msg) {
        super(msg);
    }

}
