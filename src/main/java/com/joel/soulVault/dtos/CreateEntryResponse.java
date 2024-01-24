package com.joel.soulVault.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEntryResponse {
    private String ownerName;
    private String title;
    private String body;
    private LocalDateTime dateTime;
}
