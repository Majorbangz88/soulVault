package com.joel.soulVault.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Diary")
public class Diary {

    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLocked;
}
