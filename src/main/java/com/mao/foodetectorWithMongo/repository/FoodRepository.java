package com.mao.foodetectorWithMongo.repository;

import com.mao.foodetectorWithMongo.entity.PFood;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FoodRepository extends MongoRepository<PFood,String> {
    Optional<PFood> findByName(String foodName);
}
