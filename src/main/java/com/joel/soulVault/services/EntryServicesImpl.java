package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Diary;
import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.data.repositories.EntryRepository;
import com.joel.soulVault.dtos.CreateEntryRequest;
import com.joel.soulVault.dtos.CreateEntryResponse;
import com.joel.soulVault.dtos.UpdateEntryRequest;
import com.joel.soulVault.dtos.UpdateEntryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.joel.soulVault.utils.Mapper.map;

@Service
public class EntryServicesImpl implements EntryServices{

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private DiaryServices diaryServices;
    @Override
    public CreateEntryResponse addEntry(CreateEntryRequest createEntry) {
        Diary foundDiary = diaryServices.findByUsername(createEntry.getOwnerName());
        if (foundDiary != null && !foundDiary.isLocked()) {
            checkTitleExists(createEntry.getOwnerName(), createEntry.getTitle());
            Entry newEntry = new Entry();
            CreateEntryResponse response = map(createEntry, newEntry);

            entryRepository.save(newEntry);
            return response;
        }
        throw new IllegalArgumentException("Diary is locked!");
    }

    private void checkTitleExists(String ownerName, String title) {
        Optional<Entry> foundEntry = entryRepository.findByOwnerName(ownerName, title);
        if (foundEntry.isPresent())
            throw new IllegalArgumentException("Title exists already");
    }

    @Override
    public long count() {
        return entryRepository.count();
    }


    @Override
    public void delete(String ownerName, String title) {
        Optional<Entry> foundEntry = entryRepository.findByOwnerName(ownerName, title);
        foundEntry.ifPresent(entry -> entryRepository.delete(entry));
    }

    @Override
    public void deleteAll() {
        entryRepository.deleteAll();
    }

    @Override
    public Entry findEntry(String ownerName, String title) {
        Optional<Entry> foundEntry = entryRepository.findByOwnerName(ownerName, title);
        if (foundEntry.isEmpty())
            throw new IllegalArgumentException("Entry not found!");
        return foundEntry.get();
    }

    @Override
    public List<Entry> findAll() {
        List<Entry> allEntries = entryRepository.findAll();
        return allEntries;
    }

    @Override
    public UpdateEntryResponse updateEntry(UpdateEntryRequest updateEntryRequest) {
        Entry entry = findEntry(updateEntryRequest.getOwnerName(), updateEntryRequest.getTitle());

        Entry updateEntry = map(updateEntryRequest, entry);
        entryRepository.save(updateEntry);

        UpdateEntryResponse updateResponse = new UpdateEntryResponse();
        updateResponse.setOwnerName(updateEntry.getOwnerName());
        updateResponse.setTitle(updateEntry.getTitle());
        updateResponse.setBody(updateEntry.getBody());
        updateResponse.setDateTime(updateEntry.getCurrentDateTime());
        return updateResponse;
    }




}
