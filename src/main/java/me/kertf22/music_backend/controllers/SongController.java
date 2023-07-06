package me.kertf22.music_backend.controllers;

import me.kertf22.music_backend.model.SongModel;
import me.kertf22.music_backend.repositories.SongRepository;
import me.kertf22.music_backend.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private  final StorageService storageService;
    private  final SongRepository songRepository;

    @Autowired
    public SongController(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }


    @GetMapping
    public ResponseEntity<List<SongModel>> getSongs() {
        List<SongModel> songs = songRepository.findAll();
        return ResponseEntity.ok(songs);
    }
}
