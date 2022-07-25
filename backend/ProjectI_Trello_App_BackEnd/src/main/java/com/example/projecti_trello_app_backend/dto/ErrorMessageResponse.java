package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {

    private int statusCode;

    private Date timestamp;

    private String message;

    private String description;

}
