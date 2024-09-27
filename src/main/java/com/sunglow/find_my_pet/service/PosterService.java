package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Poster;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PosterService {

    List<Poster> getAllPosters();
    Poster getPosterById(Long id);
    Poster insertPoster(Poster poster);
    Optional<Poster> updatePoster(Long id, Poster poster);
}
