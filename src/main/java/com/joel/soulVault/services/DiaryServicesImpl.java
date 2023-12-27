package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.data.repositories.DiaryRepository;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.FindEntryResponse;
import com.joel.soulVault.dtos.LoginRequests;
import com.joel.soulVault.dtos.RegistrationRequests;
import com.joel.soulVault.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joel.soulVault.utils.Mapper.map;

@Service
public class DiaryServicesImpl implements DiaryServices {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EntryServices entryServices;

    @Override
    public void register(RegistrationRequests registrationRequests) {
        validateUsername(registrationRequests.getUsername());
        Diary diary = new Diary();
        map(registrationRequests, diary);
        diaryRepository.save(diary);
    }

    private void validateUsername(String username) {
        for (Diary diary : diaryRepository.findAll()) {
            if (diary.getUsername().equals(username))
                throw new IllegalArgumentException("Username already exists");
        }
    }

    @Override

    public long count() {
        return diaryRepository.count();
    }

    @Override
    public Diary findByUsername(String username) {
//        validateUsername(username);
        for (Diary diary : diaryRepository.findAll()) {
            if (diary.getUsername().equals(username))
                return diary;
        }
        return null;
    }

    @Override
    public void delete(String username, String password) {
        Diary diary = findByUsername(username);
        if (diary.getPassword().equals(password))
            diaryRepository.delete(diary);
    }

    @Override
    public Entry addEntry(CreateEntryRequest entryRequest) {
        validateUserName(entryRequest.getUsername());
        map(entryRequest);
        Entry entry = entryServices.addEntry(entryRequest);
        return entry;
    }

    private void validateUserName(String username) {
        Diary foundDiary = diaryRepository.findByUsername(username);
        if (foundDiary == null)
            throw new IllegalArgumentException("Diary not found");
        if (foundDiary.isLocked())
            throw new IllegalArgumentException("Diary is locked");
    }


    @Override
    public FindEntryResponse findEntry(String username, String title) {
        Entry entry = entryServices.findEntry(username, title);
        FindEntryResponse findEntryResponse = new FindEntryResponse();
        map(findEntryResponse, entry);
        return findEntryResponse;
    }

    @Override
    public void lock(String username) {
        Diary foundDiary = findByUsername(username);
        foundDiary.setLocked(true);
        diaryRepository.save(foundDiary);
    }

    @Override
    public void unlock(LoginRequests loginRequests) {
        Diary diary = diaryRepository.findByUsername(loginRequests.getUsername());
        if (diary == null) throw new IllegalArgumentException("Diary not found");
        if (diary.getPassword().equals(loginRequests.getPassword()))
            diary.setLocked(false);
        else
            throw new IllegalArgumentException("password incorrect");
        diaryRepository.save(diary);
    }
}
