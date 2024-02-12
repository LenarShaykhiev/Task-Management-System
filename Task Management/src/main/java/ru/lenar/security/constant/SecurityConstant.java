package ru.lenar.security.constant;

public class SecurityConstant {
    public static final String API = "/api";
    public static final String LOGIN_FILTER_PROCESSES_URL = API + "/login";
    public static final long EXPIRATION_TIME = 604_800_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String TOKEN_SECRET = "[a-zA-Z0-9._]^+f7re87456rcdf987cr89df745fddsds45ds89";
}
