package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private int taskId;

    private String taskName;

    private String taskDescription;

    private String taskBackground;

    private Timestamp startAt;

    private Timestamp dueAt;

    private Boolean done;

    private Boolean reviewed;


}
