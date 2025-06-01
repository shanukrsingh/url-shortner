package com.author.shortener;

import java.util.Optional;

import lombok.Data;

@Data
public class UpdateUrlDTO {
    private Optional<String> url;
    private Optional<String> shortCode;
    private Optional<String> updatedAt;
}
