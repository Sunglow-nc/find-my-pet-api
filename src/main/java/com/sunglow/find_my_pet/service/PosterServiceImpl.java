package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PosterServiceImpl implements PosterService {

    @Autowired
    PosterManagerRepository posterManagerRepository;

    @Override
    public List<Poster> getAllPosters() {
        ArrayList<Poster> posters = new ArrayList<>();
        posterManagerRepository.findAll().forEach(posters::add);
        return posters;
    }

    public Poster getPosterById(Long id) {
        return posterManagerRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException("Poster not found for ID: " + id));
    }

    @Override
    public Poster insertPoster(Poster poster) {
        return posterManagerRepository.save(poster);
    }

    @Override
    public Optional<Poster> updatePoster(Long id, Poster poster) {
        Optional<Poster> updatePosterById = posterManagerRepository.findById(id);

        if (updatePosterById.isPresent()) {
            updatePosterById.get().setDatePosted(poster.getDatePosted());
            updatePosterById.get().setDescription(poster.getDescription());
            updatePosterById.get().setTitle(poster.getTitle());
            updatePosterById.get().setPet(poster.getPet());
            posterManagerRepository.save(poster);
        }

        return updatePosterById;
    }
}
