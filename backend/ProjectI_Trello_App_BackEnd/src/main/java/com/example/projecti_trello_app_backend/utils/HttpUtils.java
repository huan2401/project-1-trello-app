package com.example.projecti_trello_app_backend.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }
}
