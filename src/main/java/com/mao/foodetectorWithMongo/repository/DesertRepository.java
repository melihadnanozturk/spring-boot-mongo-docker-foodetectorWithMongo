package com.mao.foodetectorWithMongo.repository;

import com.mao.foodetectorWithMongo.entity.PDesert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DesertRepository extends MongoRepository<PDesert,String> {
    Optional<PDesert> findByName(String name);
}
