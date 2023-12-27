package com.joel.soulVault.services;

import com.joel.soulVault.data.models.Entry;
import com.joel.soulVault.data.repositories.EntryRepository;
import com.joel.soulVault.dtos.CreateEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joel.soulVault.utils.Mapper.map;

@Service
public class EntryServicesImpl implements EntryServices{

    @Autowired
    private EntryRepository entryRepository;
    @Override
    public Entry addEntry(CreateEntryRequest createEntry) {
        checkTitleExists(createEntry.getUsername(), createEntry.getTitle());
        Entry entry = new Entry();
        map(createEntry);

        entryRepository.save(entry);
        return entry;
    }

    private void checkTitleExists(String ownerName, String title) {
        Entry foundEntry = entryRepository.findByOwnerName(ownerName, title);
        if (foundEntry != null)
            throw new IllegalArgumentException("Title exists already");
    }

    @Override
    public long count() {
        return entryRepository.count();
    }


    @Override
    public void delete(String ownerName, String title) {
        Entry foundEntry = findEntry(ownerName, title);
        entryRepository.delete(foundEntry);
    }

    @Override
    public Entry findEntry(String ownerName, String title) {
        Entry foundEntry = entryRepository.findByOwnerName(ownerName, title);
        boolean entryNotFound = foundEntry == null;
        if (entryNotFound) throw new IllegalArgumentException("Entry not found");
        return foundEntry;
    }
}
