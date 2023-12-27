package com.joel.soulVault.services;

import com.joel.soulVault.dtos.CreateEntryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryServicesTest {

    @Autowired
    private EntryServices entryServices;
    CreateEntryRequest createEntry;

    @BeforeEach
    public void startWithThis() {
        entryServices = new EntryServicesImpl();

        createEntry = new CreateEntryRequest();
        createEntry.setUsername("ownerName");
        createEntry.setTitle("title");
        createEntry.setBody("body");
    }

    @Test
    public void testThatEntryCanBeAdded() {
        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());
    }

    @Test public void testThatEntryBelongingToUserCanBeDeletedByTheirTitle() {
        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

        CreateEntryRequest newEntryRequest = new CreateEntryRequest();
        newEntryRequest.setUsername("ownerName");
        newEntryRequest.setTitle("Another title");
        newEntryRequest.setBody("Another body");
        entryServices.addEntry(newEntryRequest);
        assertEquals(2, entryServices.count());

        entryServices.delete(newEntryRequest.getUsername(), newEntryRequest.getTitle());
        assertEquals(1, entryServices.count());
    }

    @Test public void testThatDeleteEntryThrowsExceptionIfEntryIsNotFount() {
        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

//        CreateEntryRequest newEntryRequest = new CreateEntryRequest();
//        newEntryRequest.setUsername("ownerName");
//        newEntryRequest.setTitle("Another title");
//        newEntryRequest.setBody("Another body");
//        entryServices.addEntry(newEntryRequest);
//        assertEquals(2, entryServices.count());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.delete("ownerName", "Another Titles"));
    }

    @Test public void testThatEntryServicesThrowsExceptionIfEntryIsNotFound() {
        entryServices.addEntry(createEntry);
        assertEquals(1, entryServices.count());

        CreateEntryRequest newEntryRequest = new CreateEntryRequest();
        newEntryRequest.setUsername("ownerName");
        newEntryRequest.setTitle("Another title");
        newEntryRequest.setBody("Another body");
        entryServices.addEntry(newEntryRequest);
        assertEquals(2, entryServices.count());

        assertThrows(IllegalArgumentException.class,
                ()-> entryServices.findEntry("ownerName", "Another title"));
    }
}