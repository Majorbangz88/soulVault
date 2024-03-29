package com.joel.soulVault.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEntryRequest {

    private String ownerName;
    private String title;
    private String body;
    private LocalDateTime dateTime;
}
