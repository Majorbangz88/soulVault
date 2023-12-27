package com.joel.soulVault.controllers;

import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.LoginRequests;
import com.joel.soulVault.dtos.RegistrationRequests;
import com.joel.soulVault.services.DiaryServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/DiaryApp")
public class DiaryController {

    private DiaryServices diaryServices;

    @PostMapping("/api/registerUser")
    public String registerUser(RegistrationRequests registrationRequests) {
        try {
            diaryServices.register(registrationRequests);
            return "Registered successfully!";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping("/api/unlockDiary")
    public String unlockDiary(LoginRequests loginRequests) {
        try {
            diaryServices.unlock(loginRequests);
            return "Unlocked successfully!";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @PostMapping("/api/lockDiary")
    public String lockDiary(String username) {
        diaryServices.lock(username);
        return "Diary locked!";
    }

    @PostMapping("/api/createEntry")
    public String createEntry(CreateEntryRequest createEntry) {
        diaryServices.addEntry(createEntry);
        return "Entry created!";
    }

    @PostMapping("/api/findEntry")
    public Object findEntryByUsername(String username, String title) {
        try {
            return diaryServices.findEntry(username, title);
        }
        catch (IllegalArgumentException error){
            return error.getMessage();
        }
    }

    }
