package com.ssafy.memberserver.common.exception;

import com.ssafy.memberserver.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {
    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
