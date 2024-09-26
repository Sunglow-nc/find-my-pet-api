package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.PosterManagerRespository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PosterServiceImpl implements PosterService {

    @Autowired
    PosterManagerRespository posterManagerRespository;

    @Override
    public List<Poster> getAllPosters() {
        ArrayList<Poster> posters = new ArrayList<>();
        posterManagerRespository.findAll().forEach(posters::add);
        return posters;
    }

    public Poster getPosterById(Long id) {
        return posterManagerRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found for ID: " + id));
    }
}
