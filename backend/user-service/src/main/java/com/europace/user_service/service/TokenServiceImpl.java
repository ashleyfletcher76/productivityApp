package com.europace.user_service.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenServiceImpl implements TokenService {
    // utilize for thread safety
    private final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public String issue(String username) {
        String token = UUID.randomUUID().toString();
        tokenMap.put(username, token);
        return token;
    }

    public Optional<String> verify(String token) {
        return tokenMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(token))
                .map(Map.Entry::getKey)
                .findFirst();
    }

//    public String issue(String username) {
//        String token = UUID.randomUUID().toString();
//        tokenMap.put(token, username);
//        return token;
//    }

//    public Optional<String> verify(String token) {
//        return Optional.ofNullable(tokenMap.get(token));
//    }
}
