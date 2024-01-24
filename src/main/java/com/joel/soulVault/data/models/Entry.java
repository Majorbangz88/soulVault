package com.joel.soulVault.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Entry")
public class Entry {

    @Id
    private String id;
    private String ownerName;
    private String title;
    private String body;
    private LocalDateTime currentDateTime;

}
