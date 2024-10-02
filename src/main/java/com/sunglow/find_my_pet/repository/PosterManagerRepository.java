package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface PosterManagerRepository extends CrudRepository<Poster, Long> {

    List<Poster> findByPet(Pet pet);
}
