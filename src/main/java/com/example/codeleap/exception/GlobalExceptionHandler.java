package com.example.codeleap.exception;

import com.example.codeleap.controller.BreedController.ImageResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BreedNotFoundException.class)
    public ResponseEntity<ImageResponse> handleBreedNotFound(BreedNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ImageResponse(null, null, ex.getMessage()));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ImageResponse> handleImageNotFound(ImageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ImageResponse(null, null, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ImageResponse> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(new ImageResponse(null, null, "Validation failed: " + ex.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ImageResponse> handleMaxSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ImageResponse(null, null, "File too large!"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ImageResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest()
                .body(new ImageResponse(null, null, "Missing parameter: " + ex.getParameterName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ImageResponse> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ImageResponse(null, null, ex.getMessage()));
    }
}
