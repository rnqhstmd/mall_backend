package com.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //UnauthorizedException
    EXPIRED_TOKEN("4010","만료된 토큰입니다."),
    INVALID_TOKEN("4011", "유효하지 않은 토큰입니다."),
    //ForbiddenException

    //NotFoundException
    TODO_NOT_FOUND("4040","TODO를 찾을 수 없습니다."),

    //ConflictException

    //ValidationException
    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다."),
    REGEX("9003", "형식에 맞지 않습니다."),
    LENGTH("9004", "길이가 유효하지 않습니다.");

    private final String code;
    private final String message;

    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> REGEX;
            case "Length" -> LENGTH;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
