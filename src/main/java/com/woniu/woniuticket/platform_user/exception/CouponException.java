package com.woniu.woniuticket.platform_user.exception;

public class CouponException extends RuntimeException {
    public CouponException() {
        super();
    }

    public CouponException(String message) {
        super(message);
    }

    public CouponException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponException(Throwable cause) {
        super(cause);
    }

    protected CouponException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
