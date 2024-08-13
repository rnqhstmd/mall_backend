package com.mall.exception;

public class DtoValidationException extends CustomException {
    public DtoValidationException(ErrorCode errorCode, String detail) {super(errorCode, detail);}
}
