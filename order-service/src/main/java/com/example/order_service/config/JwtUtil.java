package com.example.order_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.secret:change-this-secret-key-to-at-least-32-characters}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms:86400000}")
    private long jwtExpirationMs;

    public String generateToken(String username) {
        long expiresAt = System.currentTimeMillis() + jwtExpirationMs;
        String payload = username + ":" + expiresAt;
        String signature = sign(payload);
        String rawToken = payload + ":" + signature;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        String[] parts = decodeTokenParts(token);
        return parts[0];
    }

    public boolean validateToken(String token, String username) {
        String[] parts = decodeTokenParts(token);
        String payload = parts[0] + ":" + parts[1];
        String expectedSignature = sign(payload);
        boolean signatureMatches = MessageDigest.isEqual(
                expectedSignature.getBytes(StandardCharsets.UTF_8),
                parts[2].getBytes(StandardCharsets.UTF_8)
        );

        long expiresAt = Long.parseLong(parts[1]);
        boolean notExpired = System.currentTimeMillis() < expiresAt;

        return signatureMatches && notExpired && parts[0].equals(username);
    }

    private String sign(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to sign token", ex);
        }
    }

    private String[] decodeTokenParts(String token) {
        String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
        String[] parts = decoded.split(":", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }

        return parts;
    }
}
