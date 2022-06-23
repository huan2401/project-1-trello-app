package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceDTO {

    private int workspaceId;

    private String workspaceTitle;

    private String workspaceDescription;

    private String workspaceType;

    private Boolean deleted;
}
