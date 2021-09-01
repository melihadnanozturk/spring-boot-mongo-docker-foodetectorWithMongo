package com.mao.foodetectorWithMongo.repository;

import com.mao.foodetectorWithMongo.entity.PSoup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SoupRepository extends MongoRepository<PSoup,String> {
    Optional<PSoup> findByName(String test);
}
