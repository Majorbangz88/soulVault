package com.joel.soulVault.utils;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.FindEntryResponse;
import com.joel.soulVault.dtos.RegistrationRequests;

public class Mapper {

    public static void map(RegistrationRequests registerRequest, Diary diary) {
        diary.setUsername(registerRequest.getUsername());
        diary.setPassword(registerRequest.getPassword());
    }

    public static Entry map(CreateEntryRequest createEntryRequest) {
        Entry entry = new Entry();
        entry.setOwnerName(createEntryRequest.getUsername());
        entry.setTitle(createEntryRequest.getTitle());
        entry.setBody(createEntryRequest.getBody());
        return entry;
    }

    public static FindEntryResponse map(FindEntryResponse findEntryResponse, Entry entry){
        findEntryResponse.setUsername(entry.getOwnerName());
        findEntryResponse.setTitle(entry.getTitle());
        findEntryResponse.setDateOfCreation(String.format("EE DD-MM-YYYY", entry.getCurrentDateTime()));
        findEntryResponse.setTimeOfCreation(String.format("HH:MM:SS a", entry.getCurrentDateTime().toLocalTime()));
        return findEntryResponse;

    }
}
