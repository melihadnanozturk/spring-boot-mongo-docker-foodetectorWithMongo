package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PFood;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.FoodRepository;
import com.mao.foodetectorWithMongo.request.FoodRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.FoodResponse;
import com.mao.foodetectorWithMongo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FoodResponse> getAllFood() {
        List<FoodResponse> responseList;
        responseList = new ArrayList<>();

        //conversionService ile düzeltme yapılabilir
        foodRepository.findAll().forEach(x -> {
            FoodResponse response = FoodResponse.builder()
                    .name(x.getName())
                    .materials(x.getMaterials())
                    .build();
            responseList.add(response);
        });

        return responseList;
    }

    @Override
    public FoodResponse getFoodByName(String foodName) {
        PFood food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));


        FoodResponse response = FoodResponse.builder()
                .name(food.getName())
                .materials(food.getMaterials())
                .build();

        return response;
    }

    @Override
    public FoodResponse createNewFood(FoodRequest request) {
        if (ısThereAnRegister(request.getName())) {
            throw new RegisterAlredyExistsException("such a record already exists");

        }

        PFood food = PFood.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        foodRepository.save(food);

        FoodResponse response = FoodResponse.builder()
                .name(food.getName())
                .materials(food.getMaterials())
                .build();

        return response;
    }

    private boolean ısThereAnRegister(String foodName) {
        return foodRepository.findByName(foodName).isPresent();
    }

    @Override
    public FoodResponse updateFood(String name, FoodRequest foodRequest) {
        PFood food = foodRepository.findByName(name)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        food.setName(foodRequest.getName());
        food.setMaterials(foodRequest.getMaterials());
        foodRepository.save(food);

        FoodResponse response = FoodResponse.builder()
                .name(food.getName())
                .materials(food.getMaterials())
                .build();

        return response;
    }

    @Override
    public DoneResponse deleteFood(String foodName) {
        PFood food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found"));

        foodRepository.delete(food);

        DoneResponse response = new DoneResponse("deletion occurred");

        return response;
    }
}
