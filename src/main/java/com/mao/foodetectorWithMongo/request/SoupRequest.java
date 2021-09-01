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
public class SoupRequest {

    @NotEmpty(message = "{foodetector.soup.name.NotEmpty.messages}")
    @NotNull(message = "{foodetector.soup.name.NotNull.messages}")
    @Size(min = 3,max = 15,message ="{foodetector.soup.name.Size.message}" )
    private String name;

    @NotEmpty(message = "{foodetector.soup.materials.NotEmpty.message}")
    @NotNull(message = "{foodetector.soup.materials.NotNull.message}")
    private HashMap materials;
}
