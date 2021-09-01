package com.mao.foodetectorWithMongo.controller;

import com.mao.foodetectorWithMongo.request.FoodRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.FoodResponse;
import com.mao.foodetectorWithMongo.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFood(){
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @GetMapping("/{foodName}")
    public ResponseEntity<FoodResponse> getFoodByName(@PathVariable String foodName){
        return ResponseEntity.ok(foodService.getFoodByName(foodName));
    }

    @PostMapping
    public ResponseEntity<FoodResponse> createNewFood(@Valid @RequestBody FoodRequest request){
        return ResponseEntity.ok(foodService.createNewFood(request));
    }

    @PutMapping("/{foodName}")
    public ResponseEntity<FoodResponse> updateFood(@PathVariable String foodName,@Valid @RequestBody FoodRequest request){
        return ResponseEntity.ok(foodService.updateFood(foodName,request));
    }

    @DeleteMapping("/{foodName}")
    public ResponseEntity<DoneResponse> deleteFood(@PathVariable String foodName){
        return ResponseEntity.ok(foodService.deleteFood(foodName));
    }
}
