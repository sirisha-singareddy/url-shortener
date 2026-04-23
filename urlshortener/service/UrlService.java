package com.sirisha.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sirisha.urlshortener.entity.Url;
import com.sirisha.urlshortener.repository.UrlRepository;
import com.sirisha.urlshortener.exception.UrlNotFoundException;
import com.sirisha.urlshortener.dto.UrlResponseDTO;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository repository;
    private final ShortCodeGenerator generator;

    public Url createShortUrl(String originalUrl, LocalDateTime expiryTime) {

        String code;
        do {
            code = generator.generateCode();
        } while (repository.existsByShortCode(code));

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(code)
                .createdAt(LocalDateTime.now())
                .expiryTime(expiryTime)
                .clickCount(0)
                .build();

        return repository.save(url);
    }
    public Url getByCode(String code) {
        return repository.findByShortCode(code)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));
    }
    public void deleteByCode(String code) {
        Url url = repository.findByShortCode(code)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

        repository.delete(url);
    }

    public void incrementClick(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);
    }
    public UrlResponseDTO convertToDTO(Url url) {
        return UrlResponseDTO.builder()
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .createdAt(url.getCreatedAt())
                .expiryTime(url.getExpiryTime())
                .clickCount(url.getClickCount())
                .build();
    }

}
