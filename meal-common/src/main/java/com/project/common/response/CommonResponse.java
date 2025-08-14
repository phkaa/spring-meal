package com.project.common.response;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import java.util.Optional;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommonResponse<T> {
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(String message, T data) {
        return CommonResponse.<T>builder()
                .message(Optional.ofNullable(message).orElse(""))
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(null, data);
    }

    public static CommonResponse<Void> success() {
        return success(null, null);
    }

    public static CommonResponse<String> successMessage(String message) {
        return success(message, null);
    }

    public static <T> CommonResponse<T> fail(String code, String message, T data) {
        return CommonResponse.<T>builder()
                .message(Optional.ofNullable(message).orElse(Strings.EMPTY))
                .data(data)
                .build();
    }
}
