package com.mao.foodetectorWithMongo.controller;

import com.mao.foodetectorWithMongo.request.FoodRequest;
import com.mao.foodetectorWithMongo.request.SoupRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.FoodResponse;
import com.mao.foodetectorWithMongo.response.SoupResponse;
import com.mao.foodetectorWithMongo.service.SoupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/soups")
@RequiredArgsConstructor
public class SoupController {

    private final SoupService soupService;

    @GetMapping
    public ResponseEntity<List<SoupResponse>> getAllSoups(){
        return ResponseEntity.ok(soupService.getAllSoup());
    }

    @GetMapping("/{soupName}")
    public ResponseEntity<SoupResponse> getSoupByName(@PathVariable String soupName){
        return ResponseEntity.ok(soupService.getSoupByName(soupName));
    }

    @PostMapping
    public ResponseEntity<SoupResponse> createNewSoup(@Valid @RequestBody SoupRequest request){
        return ResponseEntity.ok(soupService.createNewSoup(request));
    }

    @PutMapping("/{soupName}")
    public ResponseEntity<SoupResponse> updateSoup(@PathVariable String soupName,@Valid @RequestBody SoupRequest request){
        return ResponseEntity.ok(soupService.updateSoup(soupName,request));
    }

    @DeleteMapping("/{soupName}")
    public ResponseEntity<DoneResponse> deleteSoup(@PathVariable String soupName){
        return ResponseEntity.ok(soupService.deleteSoup(soupName));
    }

}
