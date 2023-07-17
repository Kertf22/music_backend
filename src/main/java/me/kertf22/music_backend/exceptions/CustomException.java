package me.kertf22.music_backend.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }


    public HttpStatus getStatus() {
        return status;
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}