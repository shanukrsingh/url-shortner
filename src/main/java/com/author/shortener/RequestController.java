package com.author.shortener;

import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
@Slf4j
public class RequestController {
    @Autowired
    private ShortenService shortenService;

    @GetMapping("/")
    public String homePage() {
        return "<h1>This Is Homepage<h1>";
    }

    @PostMapping("/shorten")
    public Object createShortUrl(@RequestBody UrlEntity payload) {
        ResponseEntity responseBody;
        responseBody = shortenService.shorten(payload);
        return responseBody;
    }

    @GetMapping("/shorten")
    public ResponseEntity getAll() {
        ResponseEntity responseEntity = shortenService.getAllUrls();
        return responseEntity;
    }

    @GetMapping("/shorten/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        ResponseEntity responseEntity = shortenService.getById(id);
        return responseEntity;
    }

    @DeleteMapping("/shorten/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        ResponseEntity responseEntity = shortenService.deleteById(id);
        return responseEntity;
    }

    @PatchMapping("/shorten/{id}")
    public ResponseEntity updateById(@PathVariable Long id, @RequestBody UpdateUrlDTO payload) {
        ResponseEntity responseEntity = shortenService.updateById(id, payload);
        return responseEntity;
    }

}
