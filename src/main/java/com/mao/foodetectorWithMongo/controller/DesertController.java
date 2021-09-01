package com.mao.foodetectorWithMongo.controller;

import com.mao.foodetectorWithMongo.request.DesertRequest;
import com.mao.foodetectorWithMongo.response.DesertResponse;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.service.DesertService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deserts")
@RequiredArgsConstructor
public class DesertController {

    private final DesertService desertService;

    @GetMapping
    public ResponseEntity<List<DesertResponse>> getAllDesert(){
        return ResponseEntity.ok(desertService.getAllDesert());
    }

    @GetMapping("/{desertName}")
    public ResponseEntity<DesertResponse> getDesertByName(@PathVariable String desertName){
        return ResponseEntity.ok(desertService.getDesertByName(desertName));
    }

    @PostMapping
    public ResponseEntity<DesertResponse> createNewDesert(@Valid @RequestBody DesertRequest request){
        return ResponseEntity.ok(desertService.createNewDesert(request));
    }

    @PutMapping("/{desertName}")
    public ResponseEntity<DesertResponse> updateDesert(@PathVariable String desertName,@Valid @RequestBody DesertRequest request){
        return ResponseEntity.ok(desertService.updateDesert(desertName,request));
    }

    @DeleteMapping("/{desertName}")
    public ResponseEntity<DoneResponse> deleteDesert(@PathVariable String desertName){
        return ResponseEntity.ok(desertService.deleteDesert(desertName));
    }
}
