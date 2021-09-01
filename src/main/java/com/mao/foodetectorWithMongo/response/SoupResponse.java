package com.mao.foodetectorWithMongo.response;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoupResponse {

    private String name;
    private HashMap materials;
}
