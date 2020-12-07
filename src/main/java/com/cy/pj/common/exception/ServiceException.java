package com.cy.pj.common.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -4339467455735184043L;

    public ServiceException() {
    }

    public ServiceException(String massage) {
        super(massage);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
