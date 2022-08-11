package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MessageResponse {

    private String message;

    private int statusCode;

    private String status;

    public MessageResponse(String message, int statusCode ){
        this.message =message;
        this.statusCode =statusCode;
        this.status = statusCode == 200 || statusCode ==201 ? "success" : "fail";
    }
}
