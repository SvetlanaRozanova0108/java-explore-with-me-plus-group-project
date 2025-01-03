package ru.practicum.ewm.stats.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidExceptions(
            MethodArgumentNotValidException e) {
        String message = e.getMessage();
        log.debug("Получен статус 400 BAD_REQUEST {}", message, e);
        return new ErrorResponse(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        String message = e.getMessage();
        log.debug("Получен статус 400 BAD_REQUEST {}", message, e);
        return new ErrorResponse(message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllExceptions(Exception e) {
        log.debug("Получен статус 500 INTERNAL_SERVER_ERROR {}", e.getMessage(), e);
        return new ErrorResponse("Ой у нас чтото сломалось :)");
    }
}
