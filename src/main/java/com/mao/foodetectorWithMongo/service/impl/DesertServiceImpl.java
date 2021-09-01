package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PDesert;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.DesertRepository;
import com.mao.foodetectorWithMongo.request.DesertRequest;
import com.mao.foodetectorWithMongo.response.DesertResponse;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.service.DesertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DesertServiceImpl implements DesertService {

    DesertRepository desertRepository;

    @Autowired
    public DesertServiceImpl(DesertRepository desertRepository) {
        this.desertRepository = desertRepository;
    }

    @Override
    public List<DesertResponse> getAllDesert() {

        List<DesertResponse> listResponse = new ArrayList<>();

        //conversionService ile düzeltme yapılabilir
        desertRepository.findAll().forEach(x -> {
            DesertResponse response = DesertResponse.builder()
                    .name(x.getName())
                    .materials(x.getMaterials())
                    .build();
            listResponse.add(response);
        });

        return listResponse;
    }

    @Override
    public DesertResponse getDesertByName(String desertName) {
        PDesert desert = desertRepository.findByName(desertName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        DesertResponse response = DesertResponse.builder()
                .name(desert.getName())
                .materials(desert.getMaterials())
                .build();

        return response;
    }

    @Override
    //eşittir diyerek vermene gerek yok!! birde böyle dene
    public DesertResponse createNewDesert(DesertRequest request) {
        if (isThereAnyRegister(request.getName())){
            throw new RegisterAlredyExistsException("such a record already exists");
        }

        PDesert desert = PDesert.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        PDesert desert1 = desertRepository.save(desert);

        DesertResponse response = DesertResponse.builder()
                .name(desert1.getName())
                .materials(desert1.getMaterials())
                .build();

        return response;
    }

    private boolean isThereAnyRegister(String desertName){
       return desertRepository.findByName(desertName).isPresent();
    }

    @Override
    public DesertResponse updateDesert(String desertName, DesertRequest desertRequest) {
        PDesert desert = desertRepository.findByName(desertName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        desert.setName(desertRequest.getName());
        desert.setMaterials(desertRequest.getMaterials());

        desertRepository.save(desert);

        DesertResponse response = DesertResponse.builder()
                .name(desert.getName())
                .materials(desert.getMaterials())
                .build();

        return response;
    }

    @Override
    public DoneResponse deleteDesert(String name) {
        PDesert desert = desertRepository.findByName(name)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        desertRepository.delete(desert);
        DoneResponse response = new DoneResponse("deletion occurred");

        return response;
    }

}
