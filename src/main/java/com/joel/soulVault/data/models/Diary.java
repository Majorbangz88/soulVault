package com.joel.soulVault.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Diary {

    @Id
    private int id;
    private String username;
    private String password;
    private boolean isLocked;
}
