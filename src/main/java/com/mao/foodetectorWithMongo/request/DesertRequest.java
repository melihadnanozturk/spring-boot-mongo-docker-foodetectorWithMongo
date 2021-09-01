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
public class DesertRequest {

    @NotEmpty(message = "{foodetector.desert.name.NotEmpty.messages}")
    @NotNull(message = "{foodetector.desert.name.NotNull.messages}")
    @Size(min = 3,max = 15,message = "{foodetector.desert.name.Size.message}")
    private String name;

    @NotEmpty(message = "{foodetector.desert.materials.NotEmpty.message}")
    @NotNull(message = "{foodetector.desert.materials.NotNull.message}")
    private HashMap materials;
}
