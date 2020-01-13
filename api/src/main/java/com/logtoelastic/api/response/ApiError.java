package com.logtoelastic.api.response;

/**
 * Represents an api error and holds data that describe te certain error.
 * It is only used as part o the api response.
 *
 * @author Milos Stojanovic
 * @version 1.0
 * @see ApiResponse
 */
public final class ApiError {
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
