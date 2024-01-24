package com.joel.soulVault.controllers;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.*;
import com.joel.soulVault.services.DiaryServices;
import com.joel.soulVault.services.EntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/DiaryApp")
public class DiaryController {

    @Autowired
    private DiaryServices diaryServices;
    @Autowired
    private EntryServices entryServices;

    @PostMapping("/api/registration")
    public Diary registerUser(@RequestBody RegistrationRequests registrationRequests) {
        try {
            Diary registeredDiary = diaryServices.register(registrationRequests);
            return registeredDiary;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @PostMapping("/api/login")
    public Diary unlockDiary(@RequestBody LoginRequests loginRequests) {
        try {
            Diary loggedInDiary = diaryServices.unlock(loginRequests);
            return loggedInDiary;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @PostMapping("/api/lock")
    public String lockDiary(@RequestBody String username) {
        diaryServices.lock(username);
        return "Diary locked!";
    }

    @PostMapping("/api/entries")
    public CreateEntryResponse createEntry(@RequestBody CreateEntryRequest createEntry) {
        try {
            CreateEntryResponse entryResponse = entryServices.addEntry(createEntry);
            return entryResponse;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @GetMapping(path = "api/find/{ownerName}/{title}")
    public Entry findEntry(@RequestParam String ownerName, String tittle) {
        try {
            Entry foundEntry = entryServices.findEntry(ownerName, tittle);
            return foundEntry;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @DeleteMapping(path = "api/delete/{ownerName}/{title}")
    public String deleteEntry(@RequestParam String ownerName, String tittle) {
        try {
            entryServices.delete(ownerName, tittle);
            return "You have deleted your entry";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @DeleteMapping(path = "api/clear")
    public void deleteAll() {
        entryServices.deleteAll();
    }

    @GetMapping(path = "api/findAll")
    public List<Entry> findAll() {
        List<Entry> allEntries = entryServices.findAll();
        return allEntries;
    }

    @PatchMapping(path = "api/update")
    public UpdateEntryResponse updateEntry(@RequestBody UpdateEntryRequest updateEntryRequest) {
        try {
            UpdateEntryResponse updateResponse = entryServices.updateEntry(updateEntryRequest);
            return updateResponse;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
