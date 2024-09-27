package com.sunglow.find_my_pet.service;

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
    PosterManagerRepository posterManagerRespository;

    @Override
    public List<Poster> getAllPosters() {
        ArrayList<Poster> posters = new ArrayList<>();
        posterManagerRespository.findAll().forEach(posters::add);
        return posters;
    }

    public Poster getPosterById(Long id) {
        return posterManagerRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poster not found for ID: " + id));
    }

    @Override
    public Poster insertPoster(Poster poster) {
        return posterManagerRespository.save(poster);
    }

    @Override
    public Optional<Poster> updatePoster(Long id, Poster poster) {
        Optional<Poster> updateAlbumById = posterManagerRespository.findById(id);

        if (updateAlbumById.isPresent()) {
            updateAlbumById.get().setDatePosted(poster.getDatePosted());
            updateAlbumById.get().setDescription(poster.getDescription());
            updateAlbumById.get().setTitle(poster.getTitle());
            updateAlbumById.get().setPet(poster.getPet());
            posterManagerRespository.save(poster);
        }

        return updateAlbumById;
    }
}
