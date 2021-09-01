package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PDesert;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.DesertRepository;
import com.mao.foodetectorWithMongo.request.DesertRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.service.DesertService;
import com.mao.foodetectorWithMongo.response.DesertResponse;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DesertServiceImplTest {

    @Mock
    private DesertRepository desertRepository;

    private DesertService desertService;

    @BeforeEach
    public void setUp() {
        desertService = new DesertServiceImpl(desertRepository);
    }

    @Test
    void getAll_whenCallAllDesert_shouldReturnAllDesert() {
        HashMap<String, String> materials = new HashMap<>();
        materials.put("su", "test kaşığı kadar");
        materials.put("pirinç", "avuç dolusu kadar");


        PDesert desert = PDesert.builder()
                .name("test")
                .materials(materials)
                .build();

        when(desertRepository.findAll()).thenReturn(Collections.singletonList(desert));

        List<DesertResponse> responseList = desertService.getAllDesert();

        Assertions.assertEquals(desert.getName(), responseList.get(0).getName());
        Assertions.assertEquals(desert.getMaterials(), responseList.get(0).getMaterials());
    }

    @Test
    void getAll_whenResponseListHasOneRegister_shouldReturnListThatHasOneElement() {
        HashMap<String, String> materials = new HashMap<>();
        materials.put("su", "test kaşığı kadar");
        materials.put("pirinç", "avuç dolusu kadar");


        PDesert desert = PDesert.builder()
                .name("test")
                .materials(materials)
                .build();

        when(desertRepository.findAll()).thenReturn(Collections.singletonList(desert));

        List<DesertResponse> responseList = desertService.getAllDesert();

        Assertions.assertEquals(1, responseList.size());
    }

    /*@Test
    void getAll_whenThereIsNoAnyRegister_shouldThrowException(){
        when(desertRepository.findAll()).thenReturn(Stream.empty());
        Assertions.assertThrows(ThereIsNoAnyRegisterException.class,()->{
            desertService.getAllDesert();
        });
    }*/


    @Test
    void getDesertByName_whenReturnResponse_shouldReturnResponseThatSameAsEntity() {
        HashMap<String, String> materials = new HashMap<>();
        materials.put("ceviz", "1 avuç");
        materials.put("kekik", "damak zevkine göre");

        PDesert desert = PDesert.builder()
                .name("test")
                .materials(materials)
                .build();

        when(desertRepository.findByName("test")).thenReturn(Optional.ofNullable(desert));

        DesertResponse response = desertService.getDesertByName("test");

        Assertions.assertEquals(desert.getName(), response.getName());
        Assertions.assertEquals(desert.getMaterials(), response.getMaterials());
    }

    @Test
    void getDesertByName_whenRegisterNotFound_shouldThrowException() {
        when(desertRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            desertService.getDesertByName("exception");
        });
    }

    @Test
    void createNewDesert_whenWantToAddNewRegister_shouldAddSuccessfully(){
        //request (new register)
        HashMap<String, String> materials = new HashMap<>();
        materials.put("ceviz", "1 avuç");
        materials.put("kekik", "damak zevkine göre");

        DesertRequest request=new DesertRequest();
        request.setName("newRegister");
        request.setMaterials(materials);

        //created register
        PDesert desert= PDesert.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        when(desertRepository.findByName(request.getName())).thenReturn(Optional.empty());
        when(desertRepository.save(any(PDesert.class))).thenReturn(desert);

        DesertResponse response=desertService.createNewDesert(request);
        Assertions.assertEquals(request.getName(),response.getName());
        Assertions.assertEquals(request.getMaterials(),response.getMaterials());
    }

    @Test
    void createNewDesert_whenRegisterAddedBefore_shouldThrowException(){
        DesertRequest request= DesertRequest.builder()
                .name("exception")
                .build();

        when(desertRepository.findByName("exception")).thenReturn(Optional.of(new PDesert()));

        Assertions.assertThrows(RegisterAlredyExistsException.class,()->{
            desertService.createNewDesert(request);
        });
    }

    @Test
    void updateDesert_whenWantToUpdateRegister_shouldUpdateSuccessfully(){
        HashMap<String, String> materials = new HashMap<>();
        materials.put("ceviz", "1 avuç");
        materials.put("kekik", "damak zevkine göre");

        PDesert desert = PDesert.builder()
                .name("desertName")
                .materials(materials)
                .build();

        HashMap<String, String> newMaterial = new HashMap<>();
        materials.put("newCeviz", "yenilendi");
        materials.put("newKekik", "yenilendi2");

        DesertRequest desertRequest=new DesertRequest();
        desertRequest.setName("newName");
        desertRequest.setMaterials(newMaterial);

        when(desertRepository.findByName("desertName")).thenReturn(Optional.ofNullable(desert));

        DesertResponse response=desertService.updateDesert("desertName",desertRequest);

        Assertions.assertEquals(response.getName(),desertRequest.getName());
        Assertions.assertEquals(response.getMaterials(),desertRequest.getMaterials());
    }

    @Test
    void updateDesert_whenRegisterThatWasWantedToUpdateNotFound_shouldThrowException(){
        when(desertRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertService.updateDesert("desertName",new DesertRequest());
        });
    }

    @Test
    void deleteDesert_whenWork_shouldReturnDoneResponseMessage(){
        HashMap<String, String> materials = new HashMap<>();
        materials.put("ceviz", "1 avuç");
        materials.put("kekik", "damak zevkine göre");

        PDesert desert = PDesert.builder()
                .name("desertName")
                .materials(materials)
                .build();

        when(desertRepository.findByName("desertName")).thenReturn(Optional.ofNullable(desert));

        DoneResponse response=desertService.deleteDesert(desert.getName());

        Assertions.assertEquals("deletion occurred",response.getMessage());
    }

    @Test
    void deleteDesert_whenRepositoryWorksToDelete_shouldWorkOnce(){
        HashMap<String, String> materials = new HashMap<>();
        materials.put("ceviz", "1 avuç");
        materials.put("kekik", "damak zevkine göre");

        PDesert desert = PDesert.builder()
                .name("desertName")
                .materials(materials)
                .build();

        when(desertRepository.findByName("desertName")).thenReturn(Optional.ofNullable(desert));

        desertService.deleteDesert(desert.getName());

        verify(desertRepository,times(1)).delete(desert);

    }

    @Test
    void deleteDesert_whenThereIsNoAnyRegister_shouldThrowException(){
        when(desertRepository.findByName(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertService.deleteDesert("desertName");
        });
    }
}