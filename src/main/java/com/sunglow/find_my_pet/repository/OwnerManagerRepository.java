package com.sunglow.find_my_pet.repository;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Poster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerManagerRepository extends CrudRepository<Owner, Long> {
}
