package me.kertf22.music_backend.controllers;

import jakarta.validation.Valid;
import me.kertf22.music_backend.dtos.SongRecordDTO;
import me.kertf22.music_backend.model.SongModel;
import me.kertf22.music_backend.repositories.SongRepository;
import me.kertf22.music_backend.services.StorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final StorageService storageService;
    private final SongRepository songRepository;

    @Autowired
    public SongController(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }

    @Secured({})
    @GetMapping
    public ResponseEntity<List<SongModel>> getSongs() {
        List<SongModel> songs = songRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(songs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@PathVariable(value = "id") String id) {
        Optional<SongModel> song = songRepository.findById(id);

        if (song.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(song.get());
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?>
    readSong(@PathVariable(value = "id") String id) {
        Optional<SongModel> song = songRepository.findById(id);

        if (song.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found!");
        }

        Resource data = storageService.loadAsResource(song.get().getAudio_file());

        SongModel songModel = song.get();
        songModel.setViews(songModel.getViews() + 1);
        songRepository.save(songModel);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + data.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createSong(
            @RequestPart("song") @Valid SongRecordDTO songRecordDTO,
            @RequestPart("audio") MultipartFile audio,
            @RequestPart("image") MultipartFile banner
    ) {

        var songModel = new SongModel();
        BeanUtils.copyProperties(songRecordDTO, songModel);

        if (songRepository.existsSongByTitleEquals(songModel.getTitle())) {

            return ResponseEntity.badRequest().body("T:taken");

        }
        try {

            System.out.println("Uploading the file...");
            String fileName = storageService.store(audio);
            songModel.setAudio_file(fileName);
            songModel.setViews(0);
            SongModel insertedSong = songRepository.save(songModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(insertedSong);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}