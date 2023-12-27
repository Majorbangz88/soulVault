package com.joel.soulVault.dtos;

import lombok.Data;

@Data
public class CreateEntryRequest {

    private String username;
    private String title;
    private String body;
}
