package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.CreateEntryRequest;

public interface EntryServices {

    Entry addEntry(CreateEntryRequest createEntryRequest);

    long count();

    void delete(String ownerName, String anotherTitle);

    Entry findEntry(String ownerName, String title);
}
