package com.mao.foodetectorWithMongo.service;

import com.mao.foodetectorWithMongo.request.SoupRequest;
import com.mao.foodetectorWithMongo.response.DoneResponse;
import com.mao.foodetectorWithMongo.response.SoupResponse;

import java.util.List;

public interface SoupService {
    List<SoupResponse> getAllSoup();

    SoupResponse getSoupByName(String test);

    SoupResponse updateSoup(String test, SoupRequest soupRequest);

    DoneResponse deleteSoup(String test);

    SoupResponse createNewSoup(SoupRequest request);
}
