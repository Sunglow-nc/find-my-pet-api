package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Poster;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PosterManagerRespository extends CrudRepository<Poster, Long> {
}
