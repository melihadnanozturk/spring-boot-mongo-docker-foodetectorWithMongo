package com.mao.foodetectorWithMongo.service.impl;

import com.mao.foodetectorWithMongo.entity.PSoup;
import com.mao.foodetectorWithMongo.exception.RegisterAlredyExistsException;
import com.mao.foodetectorWithMongo.exception.RegisterNotFoundException;
import com.mao.foodetectorWithMongo.repository.SoupRepository;
import com.mao.foodetectorWithMongo.request.SoupRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.SoupResponse;
import com.mao.foodetectorWithMongo.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoupServiceImpl implements SoupService {

    @Autowired
    SoupRepository soupRepository;

    public SoupServiceImpl(SoupRepository soupRepository) {
        this.soupRepository = soupRepository;
    }

    @Override
    public List<SoupResponse> getAllSoup() {
        List<SoupResponse> responseList = new ArrayList<>();

        //conversionService ile düzeltme yapılabilir
        soupRepository.findAll().forEach(x -> {
            SoupResponse response = SoupResponse.builder()
                    .name(x.getName())
                    .materials(x.getMaterials())
                    .build();
            responseList.add(response);
        });

        return responseList;
    }

    @Override
    public SoupResponse getSoupByName(String soupName) {
        PSoup soup = soupRepository.findByName(soupName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        SoupResponse response = SoupResponse.builder()
                .name(soup.getName())
                .materials(soup.getMaterials())
                .build();

        return response;
    }

    @Override
    public SoupResponse createNewSoup(SoupRequest request) {
        if (isThereAnyRegister(request.getName())) {
            throw new RegisterAlredyExistsException("such a record already exists");
        }

        PSoup soup = PSoup.builder()
                .name(request.getName())
                .materials(request.getMaterials())
                .build();

        soupRepository.save(soup);

        SoupResponse response = SoupResponse.builder()
                .name(soup.getName())
                .materials(soup.getMaterials())
                .build();

        return response;
    }

    private boolean isThereAnyRegister(String soupName) {
        return soupRepository.findByName(soupName).isPresent();
    }

    @Override
    public SoupResponse updateSoup(String soupName, SoupRequest soupRequest) {
        PSoup soup = soupRepository.findByName(soupName)
                .orElseThrow(() -> new RegisterNotFoundException("Register werenot found with given name"));

        soup.setName(soupRequest.getName());
        soup.setMaterials(soupRequest.getMaterials());
        soupRepository.save(soup);

        SoupResponse response = SoupResponse.builder()
                .name(soup.getName())
                .materials(soup.getMaterials())
                .build();

        return response;
    }

    @Override
    public DoneResponse deleteSoup(String soupName) {
        PSoup soup = soupRepository.findByName(soupName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not found with given name"));

        soupRepository.delete(soup);

        DoneResponse response = new DoneResponse("deletion occurred");
        return response;
    }

}
