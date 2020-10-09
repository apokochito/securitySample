package com.security.demo.constants;

public class Constants {

    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Demo ";
    public static final String SIGN_UP_URL = "/access/singup";

    // The secret key is combined with the header and the payload to create a unique hash.
    // We are only able to verify this hash if you have the secret key.
    public static final String SECRET_KEY = "ThisIsASecretKey";
}
