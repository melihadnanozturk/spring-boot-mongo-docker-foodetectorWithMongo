package com.mao.foodetectorWithMongo.exception;

public class ThereIsNoAnyRegisterException extends RuntimeException{

    public ThereIsNoAnyRegisterException(String message){
        super(message);
    }
}
