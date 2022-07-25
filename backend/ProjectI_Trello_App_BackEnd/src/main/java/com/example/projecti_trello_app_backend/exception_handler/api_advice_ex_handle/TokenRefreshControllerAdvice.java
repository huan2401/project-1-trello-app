package com.example.projecti_trello_app_backend.exception_handler.api_advice_ex_handle;

import com.example.projecti_trello_app_backend.dto.ErrorMessageResponse;
import com.example.projecti_trello_app_backend.exception_handler.exceptions.TokenRefreshInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/*
* @RescontrollerAdvice is used for catch controller's exceptions  and give them to handlers
* */
@RestControllerAdvice
public class TokenRefreshControllerAdvice {

    @ExceptionHandler(TokenRefreshInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageResponse handleTokenRefreshInvalidException(TokenRefreshInvalidException ex, WebRequest webRequest)
    {
        return new ErrorMessageResponse(HttpStatus.UNAUTHORIZED.value(),
                                        new Date(),
                                        ex.getMessage(),
                                        webRequest.getDescription(false));
    }
}
