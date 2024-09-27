package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface PetManagerRepository extends CrudRepository<Pet, Long> {

}
