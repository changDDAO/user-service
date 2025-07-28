package com.changddao.userservice.advice;

import com.changddao.userservice.dto.response.Result;
import com.changddao.userservice.exception.AccessDeniedException;
import com.changddao.userservice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseService responseService;
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultException(Exception e) {
        return responseService.handleFailResult(500, e.getMessage());
    }


    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result failUploadFile(FileUploadException exception){
        return responseService.handleFailResult(500, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result failAccess(AccessDeniedException exception){
        return responseService.handleFailResult(401, exception.getMessage());
    }

}
