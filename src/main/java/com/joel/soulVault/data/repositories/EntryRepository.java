package com.joel.soulVault.data.repositories;

import com.joel.soulVault.data.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EntryRepository extends MongoRepository<Entry, String> {
    Optional<Entry> findByOwnerName(String ownerName, String title);
}
