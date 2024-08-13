package com.mall.exception;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {super(errorCode);}
}
