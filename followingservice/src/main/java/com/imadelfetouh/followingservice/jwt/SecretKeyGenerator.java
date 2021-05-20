package com.imadelfetouh.followingservice.jwt;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecretKeyGenerator {

    private static final SecretKeyGenerator secretKeyGenerator = new SecretKeyGenerator();
    private final String keyString = "secretkeyforkwetterimadinjwt@semester6";
    private Key key;

    public static SecretKeyGenerator getInstance(){
        return secretKeyGenerator;
    }

    public Key getKey() {
        if(key == null){
            byte[] bytes = keyString.getBytes();
            key = Keys.hmacShaKeyFor(bytes);
            return key;
        }
        return key;
    }
}
