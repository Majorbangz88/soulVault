package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.LoginRequests;
import com.joel.soulVault.dtos.RegistrationRequests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServicesTest {

    @Autowired
    private DiaryServices diaryServices;
    RegistrationRequests registrationRequests;
    CreateEntryRequest createEntry;
    @BeforeEach
    public void setup() {
        diaryServices = new DiaryServicesImpl();

        registrationRequests = new RegistrationRequests();
        registrationRequests.setUsername("username");
        registrationRequests.setPassword("password");

        createEntry = new CreateEntryRequest();
        createEntry.setUsername(registrationRequests.getUsername());
        createEntry.setTitle("title");
        createEntry.setBody("body");
    }

    @Test
    public void testThatUserCanRegister() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());

        registrationRequests.setUsername("Username");
        registrationRequests.setPassword("Password");
        diaryServices.register(registrationRequests);
        assertEquals(2, diaryServices.count());
    }

    @Test public void testThatUsername_Is_Unique_And_Throws_Exception_If_not_unique() {
        diaryServices.register(registrationRequests);
        assertThrows(IllegalArgumentException.class,
                ()-> {diaryServices.register(registrationRequests);});
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

        registrationRequests.setUsername("Username");
        registrationRequests.setPassword("Password");
        diaryServices.register(registrationRequests);
        assertEquals(2, diaryServices.count());

        diaryServices.delete("Username", "Password");
        assertEquals(1, diaryServices.count());
    }

    @Test public void testThatEntryCanBeAddedToDiary() {
        diaryServices.register(registrationRequests);
        assertEquals(1, diaryServices.count());
        diaryServices.addEntry(createEntry);

        Object object = diaryServices.findEntry("username", "title");
        assertEquals("body", object.toString());
    }

    @Test public void testThatAddEntryThrowsExceptionIfDiaryDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry(createEntry));
    }

    @Test public void testThatExceptionIsThrownWhenEntryIsAddedWhenDiaryIsLocked() {
        diaryServices.register(registrationRequests);
        diaryServices.lock("username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry(createEntry));
    }

    @Test public void testThatDiaryCanAddEntryWhenItIsUnlocked() {
        diaryServices.register(registrationRequests);
        diaryServices.lock("username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry(createEntry));

        LoginRequests loginRequest = new LoginRequests();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        diaryServices.unlock(loginRequest);
        diaryServices.addEntry(createEntry);
        Object object = diaryServices.findEntry("username", "title");
        assertEquals("body", object.toString());
    }

    @Test public void testThatDiaryIsLockedAnd_AndThrowsExceptionIfIncorrectPassword() {
        diaryServices.register(registrationRequests);
        diaryServices.lock("username");

        assertThrows(IllegalArgumentException.class,
                ()-> diaryServices.addEntry(createEntry));
    }
}