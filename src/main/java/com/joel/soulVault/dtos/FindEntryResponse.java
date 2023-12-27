package com.joel.soulVault.dtos;

import lombok.Data;

@Data
public class FindEntryResponse {

    private String username;
    private String title;
    private String dateOfCreation;
    private String timeOfCreation;
}
