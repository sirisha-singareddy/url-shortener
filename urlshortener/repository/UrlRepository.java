package com.sirisha.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sirisha.urlshortener.entity.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long>{
    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

}
