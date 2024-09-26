package com.example.spring.producer.api.exception.lang;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 801061379103093358L;

    private Boolean validation = Boolean.TRUE;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Boolean getValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }
}
