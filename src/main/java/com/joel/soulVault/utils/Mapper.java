package com.joel.soulVault.utils;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.*;

public class Mapper {

    public static void map(RegistrationRequests registerRequest, Diary diary) {
        diary.setUsername(registerRequest.getUsername());
        diary.setPassword(registerRequest.getPassword());
        diary.setLocked(true);
    }

    public static CreateEntryResponse map(CreateEntryRequest createEntryRequest, Entry entry) {
        entry.setOwnerName(createEntryRequest.getOwnerName());
        entry.setTitle(createEntryRequest.getTitle());
        entry.setBody(createEntryRequest.getBody());
        entry.setCurrentDateTime(createEntryRequest.getDateTime());

        CreateEntryResponse response = new CreateEntryResponse();
        response.setOwnerName(createEntryRequest.getOwnerName());
        response.setTitle(createEntryRequest.getTitle());
        response.setBody(createEntryRequest.getBody());
        response.setDateTime(createEntryRequest.getDateTime());

        return response;
    }

    public static FindEntryResponse map(FindEntryResponse findEntryResponse, Entry entry){
        findEntryResponse.setUsername(entry.getOwnerName());
        findEntryResponse.setTitle(entry.getTitle());
        findEntryResponse.setDateOfCreation(String.format("EE DD-MM-YYYY", entry.getCurrentDateTime()));
        findEntryResponse.setTimeOfCreation(String.format("HH:MM:SS a", entry.getCurrentDateTime().toLocalTime()));
        return findEntryResponse;
    }

    public static Entry map(UpdateEntryRequest updateEntry, Entry newEntry) {
        updateEntry.setOwnerName(newEntry.getOwnerName());
        updateEntry.setTitle(newEntry.getTitle());
        updateEntry.setBody(newEntry.getBody());
        updateEntry.setDateTime(newEntry.getCurrentDateTime());

        return newEntry;
    }
}
