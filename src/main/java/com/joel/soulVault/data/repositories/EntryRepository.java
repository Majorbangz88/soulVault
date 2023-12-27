package com.joel.soulVault.data.repositories;

import com.joel.soulVault.data.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, String> {
    Entry findByOwnerName(String ownerName, String title);
}
