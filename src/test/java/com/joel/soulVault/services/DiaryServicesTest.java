package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServicesTest {

    @Autowired
    private DiaryServices diaryServices;
    @Autowired
    private EntryServices entryServices;
    RegistrationRequests registrationRequests;
    CreateEntryRequest createEntry;
    @BeforeEach
    public void setup() {
//        diaryServices = new DiaryServices();

        registrationRequests = new RegistrationRequests();
        registrationRequests.setUsername("username");
        registrationRequests.setPassword("password");

        createEntry = new CreateEntryRequest();
        createEntry.setOwnerName(registrationRequests.getUsername());
        createEntry.setTitle("title");
        createEntry.setBody("body");
        createEntry.setDateTime(LocalDateTime.now());

        diaryServices.deleteAll();
        entryServices.deleteAll();
    }

    @Test
    public void testThatUserCanRegister() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());
    }

    @Test public void testThatUsername_Is_Unique_And_Throws_Exception_If_not_unique() {
        diaryServices.register(registrationRequests);

        RegistrationRequests registrationRequests1 = new RegistrationRequests();
        registrationRequests1.setUsername(registrationRequests.getUsername());
        registrationRequests1.setPassword(registrationRequests.getPassword());

        assertThrows(IllegalArgumentException.class,
                ()-> {diaryServices.register(registrationRequests1);});
        assertEquals(1, diaryServices.count());
    }

    @Test public void testThat_User_Can_Be_found_using_username() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());

        Diary foundUser = diaryServices.findByUsername("username");
        assertEquals("username", foundUser.getUsername());
    }

    @Test public void testThat_User_Can_Be_Deleted() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());

        diaryServices.delete("username", "password");
        assertEquals(0, diaryServices.count());
    }

    @Test public void testThatEntryCanBeAddedToDiary() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());

        CreateEntryResponse response = entryServices.addEntry(createEntry);

        assertNotNull(response);
        assertEquals(1, entryServices.count());
    }

    @Test public void testThatAddEntryThrowsExceptionIfDiaryDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));
    }

    @Test public void testThatExceptionIsThrownWhenEntryIsAddedWhenDiaryIsLocked() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");

        assertTrue(lockedDiary.isLocked());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));
    }

    @Test public void testThatDiaryCanAddEntryWhenItIsUnlocked() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");

        assertTrue(lockedDiary.isLocked());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        lockedDiary = diaryServices.unlock(loginRequest);

        assertFalse(lockedDiary.isLocked());

        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());
    }

    @Test public void testThatDiaryThrowsException_If_LoginRequestWith_IncorrectPassword() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");
        assertTrue(lockedDiary.isLocked());

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("passwords");
//        lockedDiary = diaryServices.unlock(loginRequest);

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.unlock(loginRequest));
    }

    @Test public void testThatDiaryUserCanFindEntry() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");

        assertTrue(lockedDiary.isLocked());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        lockedDiary = diaryServices.unlock(loginRequest);

        assertFalse(lockedDiary.isLocked());

        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

        Entry foundEntry = entryServices.findEntry(createEntry.getOwnerName(), createEntry.getTitle());
        assertNotNull(foundEntry);
    }

    @Test public void testThatDiaryUserCanDeleteEntry() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");

        assertTrue(lockedDiary.isLocked());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        lockedDiary = diaryServices.unlock(loginRequest);

        assertFalse(lockedDiary.isLocked());

        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

        entryServices.delete(createEntry.getOwnerName(), createEntry.getTitle());
        assertEquals(0, entryServices.count());
    }

    @Test public void testThatEntryCanBeUpdatedByDiaryUser() {
        diaryServices.register(registrationRequests);
        Diary lockedDiary = diaryServices.lock("username");

        assertTrue(lockedDiary.isLocked());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.addEntry(createEntry));

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        lockedDiary = diaryServices.unlock(loginRequest);

        assertFalse(lockedDiary.isLocked());

        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

        UpdateEntryRequest updateEntryRequest = new UpdateEntryRequest();
        updateEntryRequest.setOwnerName(registrationRequests.getUsername());
        updateEntryRequest.setTitle(createEntry.getTitle());
        updateEntryRequest.setBody(createEntry.getBody());
        updateEntryRequest.setDateTime(LocalDateTime.now());
        UpdateEntryResponse updateResponse = entryServices.updateEntry(updateEntryRequest);

        assertNotNull(updateResponse);
        assertEquals(1, entryServices.count());
    }
}