package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Poster;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PosterService {

    List<Poster> getAllPosters();
    Poster getPosterById(Long id);
}
