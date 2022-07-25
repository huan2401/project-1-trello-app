package com.example.projecti_trello_app_backend.exception_handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenRefreshInvalidException extends RuntimeException {

    private static final long serialVersionUID =1L;

    public TokenRefreshInvalidException(String token, String message)
    {
            super(String.format("Refresh Token [%s] is invalid : %s", token,message));
    }
}
