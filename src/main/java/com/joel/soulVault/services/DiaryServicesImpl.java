package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.data.repositories.DiaryRepository;
import com.joel.soulVault.dtos.*;
import com.joel.soulVault.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.joel.soulVault.utils.Mapper.map;

@Service
public class DiaryServicesImpl implements DiaryServices {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EntryServices entryServices;

    @Override
    public Diary register(RegistrationRequests registrationRequests) {
        validateUsername(registrationRequests.getUsername());
        Diary diary = new Diary();
        Mapper.map(registrationRequests, diary);
        diaryRepository.save(diary);
        return diary;
    }

    private void validateUsername(String username){
        Optional<Diary> optionalDiary = diaryRepository.findByUsername(username);
        if (optionalDiary.isPresent()) throw new IllegalArgumentException("Username taken!");
    }

    @Override

    public long count() {
        return diaryRepository.count();
    }

    @Override
    public Diary findByUsername(String username) {
        Optional<Diary> foundUser = diaryRepository.findByUsername(username);
        if (foundUser.isPresent())
            return foundUser.get();
        throw new IllegalArgumentException("Diary not found");
    }

    @Override
    public void delete(String username, String password) {
        Diary diary = findByUsername(username);
        if (diary.getPassword().equals(password))
            diaryRepository.delete(diary);
    }

//    @Override
//    public CreateEntryResponse createEntry(CreateEntryRequest entryRequest) {
//        CreateEntryResponse response = entryServices.addEntry(entryRequest);
//        return response;
//    }

    private void validateUserName(String username) {
        Optional<Diary> foundDiary = diaryRepository.findByUsername(username);
        if (foundDiary.isEmpty())
            throw new IllegalArgumentException("Diary not found");
        if (foundDiary.get().isLocked())
            throw new IllegalArgumentException("Diary is locked");
    }


//    @Override
//    public FindEntryResponse findEntry(String username, String title) {
//        Optional<Entry> entry = entryServices.findEntry(username, title);
//        FindEntryResponse findEntryResponse = new FindEntryResponse();
//        Mapper.map(findEntryResponse, entry);
//        return findEntryResponse;
//    }

    @Override
    public Diary lock(String username) {
        Diary foundDiary = findByUsername(username);
        foundDiary.setLocked(true);
        diaryRepository.save(foundDiary);

        return foundDiary;
    }

    @Override
    public Diary unlock(LoginRequests loginRequests) {
        Optional<Diary> diary = diaryRepository.findByUsername(loginRequests.getUsername());
        if (diary.isEmpty()) throw new IllegalArgumentException("Diary not found");
        if (diary.get().getPassword().equals(loginRequests.getPassword()))
            diary.get().setLocked(false);
        else
            throw new IllegalArgumentException("password incorrect");
        diaryRepository.save(diary.get());

        return diary.get();
    }

    @Override
    public void deleteAll() {
        diaryRepository.deleteAll();
    }
}

