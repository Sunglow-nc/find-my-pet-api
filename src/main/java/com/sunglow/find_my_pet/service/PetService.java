package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Pet;

import java.util.List;

public interface PetService {
    List<Pet>  getPetsByColour(String colour);
    List<Pet>  getPetsByType(String type);
}
