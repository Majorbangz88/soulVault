package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.dtos.*;

public interface DiaryServices {

    Diary register(RegistrationRequests registrationRequests);
    long count();
    Diary findByUsername(String username);
    void delete(String username, String password);

//    CreateEntryResponse createEntry(CreateEntryRequest createEntry);

//    FindEntryResponse findEntry(String username, String title);

    Diary lock(String username);

    Diary unlock(LoginRequests loginRequests);

    void deleteAll();
}
