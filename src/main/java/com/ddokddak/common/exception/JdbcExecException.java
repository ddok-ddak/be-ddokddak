package com.ddokddak.common.exception;

import com.ddokddak.common.exception.type.ExceptionType;

public class JdbcExecException  extends CustomApiException {
    public JdbcExecException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
