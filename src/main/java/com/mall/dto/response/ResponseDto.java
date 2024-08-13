package com.mall.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final String statusCode;
    private final String message;
    private final T data;

    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message) {
        return new ResponseDto<>(statusCode.toString(), message, null);
    }

    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message, final T data) {
        return new ResponseDto<>(statusCode.toString(), message, data);
    }
}
