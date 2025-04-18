package com.ecart.exceptioncommon.base;

import com.ecart.exceptioncommon.err.ErrorMessage;

public class BaseException extends RuntimeException {

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
    }
}
