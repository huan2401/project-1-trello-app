package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDTO {

    private int columnId;

    private int boardId;

    private String columnTitle;

    private String status;

    private Integer position;
}
