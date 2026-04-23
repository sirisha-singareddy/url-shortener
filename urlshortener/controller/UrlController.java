package com.sirisha.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.sirisha.urlshortener.service.UrlService;
import com.sirisha.urlshortener.entity.Url;

import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService service;

    @PostMapping("/api/shorten")
    public ResponseEntity<?> shorten(@RequestParam String url,
                                     @RequestParam(required = false) Long expiryMinutes) {

        LocalDateTime expiry = null;

        if (expiryMinutes != null) {
            expiry = LocalDateTime.now().plusMinutes(expiryMinutes);
        }

        Url created = service.createShortUrl(url, expiry);

        String shortUrl = "http://localhost:8080/" + created.getShortCode();

        Map<String, Object> response = new HashMap<>();
        response.put("shortUrl", shortUrl);
        response.put("createdAt", created.getCreatedAt());
        response.put("expiryTime", created.getExpiryTime());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {

        Url url = service.getByCode(code);

        if (url.getExpiryTime() != null &&
                url.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Link expired");
        }

        service.incrementClick(url);

        return ResponseEntity
                .status(302)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }

    @GetMapping("/api/stats/{code}")
    public ResponseEntity<?> stats(@PathVariable String code) {

        Url url = service.getByCode(code);
        return ResponseEntity.ok(service.convertToDTO(url));
    }
    @DeleteMapping("/api/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {

        service.deleteByCode(code);

        return ResponseEntity.ok("Deleted successfully");
    }

}
