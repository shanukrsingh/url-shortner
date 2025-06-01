package com.author.shortener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShortenService {
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UpdateUrlMapper updateUrlMapper;

    boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
            // log.error("bad request error: url is invalid: {}", e.getMessage());
            // return false;
        }
    }

    public ResponseEntity shorten(UrlEntity urlRequest) {
        // check if url is valid
        if (!isValidUrl(urlRequest.getUrl())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL is invalid");
        }

        String currentTime = Instant.now().toString();
        urlRequest.setCreatedAt(currentTime);
        urlRequest.setUpdatedAt(currentTime);

        String shortUrl;
        do {
            shortUrl = UUID.randomUUID().toString().substring(0, 6);
        } while (urlRepository.findByShortCode(shortUrl).isPresent());

        urlRequest.setShortCode(shortUrl);
        urlRepository.save(urlRequest);
        log.info("new url entity created: {}", urlRequest.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(urlRequest);
    }

    public ResponseEntity getAllUrls() {
        // get all url objects
        List<UrlEntity> foundObjects = urlRepository.findAll();

        log.info("url entities found {}", foundObjects.size());
        return ResponseEntity.status(HttpStatus.OK).body(foundObjects);
    }

    public ResponseEntity getById(Long id) {
        Optional<UrlEntity> urlEntity = urlRepository.findById(id);
        if (!urlEntity.isPresent()) {
            throw new EntityNotFoundException("no record found by this id " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(urlEntity);
    }

    public ResponseEntity deleteById(Long id) {
        Optional<UrlEntity> urlEntity = urlRepository.findById(id);
        if (urlEntity.isPresent()) {
            urlRepository.deleteById(id);
            log.info("deleted entity with id {}", id);
        } else {
            throw new EntityNotFoundException("no record found by this id " + id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity updateById(Long id, UpdateUrlDTO payload) {
        Optional<UrlEntity> urlEntity = urlRepository.findById(id);
        if (urlEntity.isPresent()) {
            updateUrlMapper.updateUserFromDto(payload, urlEntity.get());
            urlRepository.save(urlEntity.get());
            log.info("updated entity with id {}", id);
        } else {
            throw new EntityNotFoundException("no record found by this id " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(urlEntity);
    }

}
