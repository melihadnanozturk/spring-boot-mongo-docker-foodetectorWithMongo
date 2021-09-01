package com.mao.foodetectorWithMongo.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRequest {

    @NotEmpty(message = "{foodetector.food.name.NotEmpty.message}")
    @NotNull(message = "{foodetector.food.name.NotNull.message}")
    @Size(min = 3,max = 15,message = "{foodetector.food.name.Size.message}")
    private String name;

    @NotEmpty(message = "{foodetector.food.materials.NotEmpty.message}")
    @NotNull(message = "{foodetector.food.materials.NotNull.message}")
    private HashMap materials;
}
