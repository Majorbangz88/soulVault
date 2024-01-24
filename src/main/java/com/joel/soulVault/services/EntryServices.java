package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.CreateEntryResponse;
import com.joel.soulVault.dtos.UpdateEntryRequest;
import com.joel.soulVault.dtos.UpdateEntryResponse;

import java.util.List;

public interface EntryServices {

    CreateEntryResponse addEntry(CreateEntryRequest createEntryRequest);

    long count();

    void delete(String ownerName, String anotherTitle);

    void deleteAll();

    Entry findEntry(String ownerName, String title);

    List<Entry> findAll();

    UpdateEntryResponse updateEntry(UpdateEntryRequest updateEntryRequest);
}
