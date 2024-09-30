package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Poster;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PosterManagerRepository extends CrudRepository<Poster, Long> {

    Optional<Poster> findByPetColour(String colour);
}
