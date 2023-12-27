package com.joel.soulVault.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Entry {

    @Id
    private int id;
    private String ownerName;
    private String title;
    private String body;
    private final LocalDateTime currentDateTime = LocalDateTime.now();

}
