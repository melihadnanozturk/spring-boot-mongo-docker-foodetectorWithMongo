package com.mao.foodetectorWithMongo.service;

import com.mao.foodetectorWithMongo.request.DesertRequest;
import com.mao.foodetectorWithMongo.response.DesertResponse;
import com.mao.foodetectorWithMongo.response.DoneResponse;

import java.util.List;

public interface DesertService {

    List<DesertResponse> getAllDesert();

    DesertResponse getDesertByName(String test);

    DesertResponse updateDesert(String desertName, DesertRequest desertRequest);

    DoneResponse deleteDesert(String name);

    DesertResponse createNewDesert(DesertRequest request);
}
