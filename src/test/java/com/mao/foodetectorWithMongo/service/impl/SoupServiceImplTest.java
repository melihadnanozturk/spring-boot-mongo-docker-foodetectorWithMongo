package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PSoup;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.SoupRepository;
import com.mao.foodetectorWithMongo.request.SoupRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.SoupResponse;
import com.mao.foodetectorWithMongo.service.SoupService;
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
class SoupServiceImplTest {

    @Mock
    SoupRepository soupRepository;

    SoupService soupService;

    @BeforeEach
    void setUp(){
        soupService=new SoupServiceImpl(soupRepository);
    }

    @Test
    void getAllSoup_whenCallAllSoup_shouldReturnAllSoup(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();

        when(soupRepository.findAll()).thenReturn(Collections.singletonList(soup));

        List<SoupResponse> responseList=soupService.getAllSoup();

        Assertions.assertEquals(soup.getName(),responseList.get(0).getName());
        Assertions.assertEquals(soup.getMaterials(),responseList.get(0).getMaterials());
    }

    @Test
    void getAllSoup_whenCall_shouldRetunrnListThatHasOneElement(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();
        when(soupRepository.findAll()).thenReturn(Collections.singletonList(soup));

        List<SoupResponse> responseList=soupService.getAllSoup();
        Assertions.assertEquals(1,responseList.size());
    }

    @Test
    void getSoupByName_whenCallWithName_shouldReturnResponseThatSameAsEntity(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();
        when(soupRepository.findByName("test")).thenReturn(Optional.ofNullable(soup));

        SoupResponse response=soupService.getSoupByName("test");

        Assertions.assertEquals(soup.getName(),response.getName());
        Assertions.assertEquals(soup.getMaterials(),response.getMaterials());
    }

    @Test
    void getSoupByName_whenThereIsNoAnyRegisterWithGivenName_shouldThrowException(){
        when(soupRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupService.getSoupByName("exception");
        });
    }

    @Test
    void createNewSoup_whenWantToAddNewRegister_shouldAddSuccessfully(){
        HashMap materials=new HashMap();
        materials.put("malzeme1","test1");
        materials.put("malzeme2","test2");

        SoupRequest request= SoupRequest.builder()
                .name("test")
                .materials(materials)
                .build();

        HashMap soupMaterials=new HashMap();
        soupMaterials.put("malzeme1","test1");
        soupMaterials.put("malzeme2","test2");

        PSoup soup= PSoup.builder()
                .name("test")
                .materials(soupMaterials)
                .build();

        when(soupRepository.findByName("test")).thenReturn(Optional.empty());
        when(soupRepository.save(any(PSoup.class))).thenReturn(soup);

        SoupResponse response=soupService.createNewSoup(request);
        Assertions.assertEquals(response.getName(),soup.getName());
        Assertions.assertEquals(response.getMaterials(),soup.getMaterials());
    }

    @Test
    void createNewSoup_whenRegisterAlreadyExists_shouldThrowException(){
        SoupRequest request= SoupRequest.builder()
                .name("test")
                .build();
        when(soupRepository.findByName("test")).thenReturn(Optional.of(new PSoup()));

        Assertions.assertThrows(RegisterAlredyExistsException.class,()->{
           soupService.createNewSoup(request);
        });
    }

    @Test
    void updateSoup_whenWantToUpdate_shouldUpdateSuccessfully(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();

        HashMap<String,String> newMaterials=new HashMap();
        materials.put("newMalzeme1","new yeterince koyun");
        materials.put("newMazleme2","new üstüne serpiştirin");

        SoupRequest soupRequest=SoupRequest.builder()
                .name("newName")
                .materials(newMaterials)
                .build();

        when(soupRepository.findByName("test")).thenReturn(Optional.ofNullable(soup));

        SoupResponse response=soupService.updateSoup("test",soupRequest);
    }

    @Test
    void updateSoup_whenThereIsNoAnyRegisterWithGivenName_shouldThrowException(){
        when(soupRepository.findByName(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupService.updateSoup("exception",any(SoupRequest.class));
        });
    }

    @Test
    void deleteSoup_whenWork_shouldReturnResponseMessage(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();

        when(soupRepository.findByName("test")).thenReturn(Optional.ofNullable(soup));

        DoneResponse response=soupService.deleteSoup("test");
        Assertions.assertEquals("deletion occurred",response.getMessage());
    }

    @Test
    void deleteSoup_whenRepositoryWorksToDelete_shouldWorkOnce(){
        HashMap<String,String> materials=new HashMap();
        materials.put("mazleme1","yeterince koyun");
        materials.put("mazleme2","üstüne serpiştirin");

        PSoup soup=PSoup.builder()
                .name("test")
                .materials(materials)
                .build();

        when(soupRepository.findByName("test")).thenReturn(Optional.ofNullable(soup));

        DoneResponse response=soupService.deleteSoup("test");
        verify(soupRepository,times(1)).delete(soup);
    }

    @Test
    void deleteSoup_whenThereIsNoAnyRegisterWithGivenName_ShouldThrowException(){
        when(soupRepository.findByName("test")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupService.deleteSoup("test");
        });
    }
}