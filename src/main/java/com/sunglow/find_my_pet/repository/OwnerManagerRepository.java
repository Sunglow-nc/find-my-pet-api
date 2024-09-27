package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Poster;
import org.springframework.data.repository.CrudRepository;

public interface OwnerManagerRepository extends CrudRepository<Owner, Long> {
}
