package com.woniu.woniuticket.platform_user.exception;

public class UserCenterException extends RuntimeException {
    public UserCenterException() {
        super();
    }

    public UserCenterException(String message) {
        super(message);
    }

    public UserCenterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCenterException(Throwable cause) {
        super(cause);
    }

    protected UserCenterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
