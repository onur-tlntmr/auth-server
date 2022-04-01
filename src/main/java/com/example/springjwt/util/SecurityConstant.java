package com.example.springjwt.util;


public class SecurityConstant {

    public static final String KEY = "secret";

    public static final String ISSUER = "example.com";

    // 5 hour for development
    public static final int EXPIRES_ACCESS_TOKEN = 5 * 60 * 60 * 1000;

    // Expires date in months
    public static final int EXPIRES_MONTH_REFRESH_TOKEN = 6;

}
