package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PosterManagerRepository posterManagerRepository;

    @Autowired
    PetManagerRepository petManagerRepository;

    @Autowired
    private OwnerManagerRepository ownerManagerRepository;

    @Override
    public List<Pet> getPetsByColour(String colour) {
        List<Pet> petsByColour = petManagerRepository.findByColour(colour);
        if (petsByColour.isEmpty()) {
            throw new ItemNotFoundException(String.format("No Pets found with colour %s", colour));
        }
        return petsByColour;
    }

    @Override
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsByType = petManagerRepository.findByType(type);
        if (petsByType.isEmpty()) {
            throw new ItemNotFoundException(String.format("No Pets found with type %s", type));
        }
        return petsByType;
    }
}
