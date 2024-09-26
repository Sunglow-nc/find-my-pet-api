package com.sunglow.find_my_pet.controller;

import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PosterController {

    @Autowired
    PosterService posterService;

    @GetMapping("/posters")
    public ResponseEntity<List<Poster>> getAllPosters() {
        return new ResponseEntity<List<Poster>>(posterService.getAllAlbums(), HttpStatus.OK);
    }

}
