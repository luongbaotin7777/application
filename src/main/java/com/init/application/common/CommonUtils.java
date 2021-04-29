package com.init.application.common;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class CommonUtils {
    //pageable
    public static final String totalPage = "totolPages";
    public static final String totalElements = "totalElements";
    public static final String numberOfElements = "numberOfElements";
    public static final String resultContent = "resultContent";
    //role
    public static final String ROLE_USER = "User";

    public static final Date NOW = new Date();
}
