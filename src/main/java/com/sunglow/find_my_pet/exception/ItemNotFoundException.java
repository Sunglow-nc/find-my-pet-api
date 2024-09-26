package com.sunglow.find_my_pet.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}