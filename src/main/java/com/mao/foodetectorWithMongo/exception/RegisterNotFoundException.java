package com.mao.foodetectorWithMongo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegisterNotFoundException extends RuntimeException{

    public RegisterNotFoundException(String message){
        super(message);
    }
}
