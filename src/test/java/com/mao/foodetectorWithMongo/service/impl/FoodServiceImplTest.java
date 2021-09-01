package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PFood;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.FoodRepository;
import com.mao.foodetectorWithMongo.request.FoodRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.FoodResponse;
import com.mao.foodetectorWithMongo.service.FoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {

    @Mock
    FoodRepository foodRepository;

    FoodService foodService;

    @BeforeEach
    void setUp() {
        foodService = new FoodServiceImpl(foodRepository);
    }

    @Test
    void getAllFood_whenCallAllFood_shouldReturnAllFood() {
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("test")
                .materials(materials)
                .build();

        when(foodRepository.findAll()).thenReturn(Collections.singletonList(food));

        List<FoodResponse> responseList = foodService.getAllFood();

        Assertions.assertEquals(food.getName(), responseList.get(0).getName());
        Assertions.assertEquals(food.getMaterials(), responseList.get(0).getMaterials());
    }

    @Test
    void getAllFood_whenThereIsOneRegister_shouldReturnListThatHasOneElement(){
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("test")
                .materials(materials)
                .build();

        when(foodRepository.findAll()).thenReturn(Collections.singletonList(food));
        List<FoodResponse> responseList = foodService.getAllFood();

        Assertions.assertEquals(1,responseList.size());
    }

    @Test
    void getFoodByName_whenCallWithName_shouldReturnResponseThatSameAsEntity(){
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("foodName")
                .materials(materials)
                .build();

        when(foodRepository.findByName("foodName")).thenReturn(Optional.ofNullable(food));

        FoodResponse response=foodService.getFoodByName("foodName");

        Assertions.assertEquals(food.getName(),response.getName());
        Assertions.assertEquals(food.getMaterials(),response.getMaterials());
    }

    @Test
    void getFoodByName_whenRegisterNotFound_shouldThrowException(){
        when(foodRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            foodService.getFoodByName("exception");
        });
    }

    @Test
    void createNewFood_whenWantToAddNewRegister_shouldAddSuccessfully(){
        HashMap<String,String> materials=new HashMap<>();
        materials.put("test","malzeme1");
        materials.put("test2","malzeme2");

        //request
        FoodRequest request= FoodRequest.builder()
                .name("test")
                .materials(materials)
                .build();

        PFood food= PFood.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        when(foodRepository.findByName("test")).thenReturn(Optional.empty());
        when(foodRepository.save(any(PFood.class))).thenReturn(food);

        FoodResponse response=foodService.createNewFood(request);

        Assertions.assertEquals(request.getName(),response.getName());
        Assertions.assertEquals(request.getMaterials(),response.getMaterials());
    }

    @Test
    void createNewFood_whenRepositoryWorksToSave_shouldWorkOnce(){
        HashMap<String,String> materials=new HashMap<>();
        materials.put("test","malzeme1");
        materials.put("test2","malzeme2");

        //request
        FoodRequest request= FoodRequest.builder()
                .name("test")
                .materials(materials)
                .build();

        PFood food= PFood.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        when(foodRepository.findByName("test")).thenReturn(Optional.empty());
        when(foodRepository.save(any(PFood.class))).thenReturn(food);

        foodService.createNewFood(request);

        verify(foodRepository,times(1)).save(any(PFood.class));
    }

    @Test
    void createNewFood_whenRegisterAlreadyExists_shouldThrowException(){
        FoodRequest request= FoodRequest.builder()
                .name("test")
                .build();

        when(foodRepository.findByName("test")).thenReturn(Optional.of(new PFood()));

        Assertions.assertThrows(RegisterAlredyExistsException.class,()->{
            foodService.createNewFood(request);
        });
    }


    @Test
    void updateFood_whenWantToUpdateFood_shouldUpdateSuccessfully(){
        //pFood
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("foodName")
                .materials(materials)
                .build();

        //request
        HashMap newMaterial = new HashMap();
        materials.put("newEt", "yeniMateryaldir");
        materials.put("newNohut", "ikinci yeni mateyal");

        FoodRequest foodRequest=FoodRequest.builder()
                .name("newName")
                .materials(newMaterial)
                .build();

        when(foodRepository.findByName("foodName")).thenReturn(Optional.ofNullable(food));

        FoodResponse response=foodService.updateFood(food.getName(),foodRequest);

        Assertions.assertEquals(food.getName(),foodRequest.getName());
        Assertions.assertEquals(food.getMaterials(),foodRequest.getMaterials());
    }

    @Test
    void updateFood_whenRegisterNotFound_shouldThrowException(){
        when(foodRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            foodService.updateFood("exception",any(FoodRequest.class));
        });
    }

    @Test
    void deleteFood_whenWork_shouldReturnDoneResponseMessage(){
        //pFood
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("foodName")
                .materials(materials)
                .build();

        when(foodRepository.findByName("foodName")).thenReturn(Optional.ofNullable(food));
        DoneResponse response=foodService.deleteFood("foodName");

        Assertions.assertEquals("deletion occurred",response.getMessage());
    }


    @Test
    void deleteFood_whenRepositoryWorksToDelete_shouldWorkOnce(){
        //pFood
        HashMap materials = new HashMap();
        materials.put("et suyu", "kab alıncaya kadar");
        materials.put("nohut", "kişi başı 1 bardak");

        PFood food = PFood.builder()
                .name("foodName")
                .materials(materials)
                .build();

        when(foodRepository.findByName("foodName")).thenReturn(Optional.ofNullable(food));

        foodService.deleteFood("foodName");

        verify(foodRepository,times(1)).delete(food);
    }

    @Test
    void deleteFood_whenRegisterNotFound_shouldThrowException(){
        when(foodRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            foodService.deleteFood("exception");
        });
    }
}