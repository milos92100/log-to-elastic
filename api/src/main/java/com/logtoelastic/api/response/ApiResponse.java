package com.logtoelastic.api.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents rest api response for all controllers.
 *
 * @author Milos Stojanovic
 * @version 1.0
 */
public final class ApiResponse {
    private boolean success;
    private Object data;
    private List<ApiError> errors;

    /**
     * Constructor
     *
     * @param success status flag
     * @param data    response dto
     * @param errors  list of errors
     */
    public ApiResponse(boolean success, Object data, List<ApiError> errors) {
        this.success = success;
        this.data = data;
        this.errors = errors;
    }

    /**
     * Creates a successful api response accepting only response dto.
     * Status is by default true and errors are empty.
     *
     * @param data response dto
     * @return ApiResponse
     */
    public static ApiResponse success(Object data) {
        return new ApiResponse(true, data, new ArrayList<>());
    }

    /**
     * Creates a failed api response accepting only error list.
     * Status is by default false and data is null.
     *
     * @param errors list of errors
     * @return ApiResponse
     */
    public static ApiResponse failure(List<ApiError> errors) {
        return new ApiResponse(false, null, errors);
    }

    /**
     * Gets the success status
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Response DTO
     *
     * @return Object
     */
    public Object getData() {
        return data;
    }

    /**
     * Gets the list of errors for failed action
     *
     * @return list of errors
     */
    public List<ApiError> getErrors() {
        return errors;
    }
}
