package com.chalyk.taskmanager.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/auth/**";

    public static final String SECRET = "SecretKeyGenJWT";

    public static final String TOKEN_PREFIX = "TaskMAcc ";

    public static final String HEADER_STRING = "Authorization";

    public static final String CONTENT_TYPE = "application/json";
}
