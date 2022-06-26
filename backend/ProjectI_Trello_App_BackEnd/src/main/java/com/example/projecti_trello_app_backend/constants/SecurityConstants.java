package com.example.projecti_trello_app_backend.constants;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    public static final String SECURITY_KEY ="@Project$1@Project$Management@Zalo@hHw63Hdjiow9";

    public static final long EXPIRE_TIME = 90*60*1000; // 90m

    public static final String AUTH_HEADER = "Authorization";

    public static final String JWT_TYPE ="Bearer ";
}
