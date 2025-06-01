package com.author.shortener;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    // Custom queries can be added here
    Optional<UrlEntity> findByShortCode(String shortCode);
}
