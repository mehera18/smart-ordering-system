package com.example.order_service.controller;

import com.example.order_service.config.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtUtil jwtUtil;

    public TokenController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/generate")
    public Map<String, String> generateToken(@RequestParam(defaultValue = "testuser") String username) {
        String token = jwtUtil.generateToken(username);
        return Map.of("token", token, "usage", "Authorization: Bearer " + token);
    }
}
