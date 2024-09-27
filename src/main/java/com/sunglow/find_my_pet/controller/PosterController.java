package com.sunglow.find_my_pet.controller;

import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PosterController {

    @Autowired
    PosterService posterService;

    @GetMapping("/posters")
    public ResponseEntity<List<Poster>> getAllPosters() {
        return new ResponseEntity<List<Poster>>(posterService.getAllPosters(), HttpStatus.OK);
    }

    @GetMapping("/posters/{id}")
    public ResponseEntity<Poster> getPosterById(@PathVariable Long id) {
        return new ResponseEntity<Poster>(posterService.getPosterById(id), HttpStatus.FOUND);
    }


    @PostMapping("/posters")
    public ResponseEntity<Poster> postPoster(@RequestBody Poster poster) {
        Poster poster1 = posterService.insertPoster(poster);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("posters", "/api/v1/posters" + poster1.getId().toString());

        return new ResponseEntity<>(poster1, httpHeaders, HttpStatus.CREATED);

    }

    @PutMapping("/posters/{id}")
    public ResponseEntity<Poster> updatePosterById(@PathVariable Long id, @RequestBody Poster poster) {
        Optional<Poster> updatePoster = posterService.updatePoster(id, poster);

        if (updatePoster.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("posters", "/api/v1/posters" + updatePoster.get().getId().toString());

        return new ResponseEntity<>(updatePoster.get(), httpHeaders, HttpStatus.OK);
    }

}
