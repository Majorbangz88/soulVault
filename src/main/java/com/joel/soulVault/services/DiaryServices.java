package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.FindEntryResponse;
import com.joel.soulVault.dtos.LoginRequests;
import com.joel.soulVault.dtos.RegistrationRequests;

public interface DiaryServices {

    void register(RegistrationRequests registrationRequests);
    long count();
    Diary findByUsername(String username);
    void delete(String username, String password);

    Entry addEntry(CreateEntryRequest createEntry);

    FindEntryResponse findEntry(String username, String title);

    void lock(String username);

    void unlock(LoginRequests loginRequests);
}
