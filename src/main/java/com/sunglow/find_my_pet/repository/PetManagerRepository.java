package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetManagerRepository extends CrudRepository<Pet, Long> {

    List<Pet> findByColour(String colour);
    List<Pet> findByType(String type);
}
