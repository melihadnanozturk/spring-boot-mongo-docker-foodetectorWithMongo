package com.mao.foodetectorWithMongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PFood {

    @Id
    private String id;
    private String name;
    private HashMap<String,String> materials;
}
