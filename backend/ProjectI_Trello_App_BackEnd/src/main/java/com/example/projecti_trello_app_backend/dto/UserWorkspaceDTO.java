package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkspaceDTO {

    private int id;

    private Integer userId;

    private Integer workspaceId;

    private String roleName;

    private Boolean deleted;
}
