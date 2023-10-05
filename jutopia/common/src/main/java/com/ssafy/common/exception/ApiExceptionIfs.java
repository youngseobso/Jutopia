package com.ssafy.common.exception;

import com.ssafy.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();


}