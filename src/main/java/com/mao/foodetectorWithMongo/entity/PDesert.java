package com.mao.foodetectorWithMongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PDesert {

    @Id
    private String id;
    private String name;
    private HashMap<String,String> materials;
}
