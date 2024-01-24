package com.joel.soulVault.data.repositories;

import com.joel.soulVault.data.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DiaryRepository extends MongoRepository<Diary, String> {
    Optional<Diary> findByUsername(String username);
}
