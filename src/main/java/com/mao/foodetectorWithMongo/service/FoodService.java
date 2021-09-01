package com.mao.foodetectorWithMongo.service;

import com.mao.foodetectorWithMongo.request.FoodRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.FoodResponse;

import java.util.List;

public interface FoodService {
    List<FoodResponse> getAllFood();

    FoodResponse getFoodByName(String foodName);

    FoodResponse updateFood(String name, FoodRequest foodRequest);

    DoneResponse deleteFood(String foodName);

    FoodResponse createNewFood(FoodRequest request);
}
