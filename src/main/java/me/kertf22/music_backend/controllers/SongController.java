package me.kertf22.music_backend.controllers;

import jakarta.validation.Valid;
import me.kertf22.music_backend.dtos.SongRecordDTO;
import me.kertf22.music_backend.dtos.UploadDTO;
import me.kertf22.music_backend.domain.DomainSong;
import me.kertf22.music_backend.enums.StorageType;
import me.kertf22.music_backend.exceptions.CustomException;
import me.kertf22.music_backend.model.FileModel;
import me.kertf22.music_backend.model.SongModel;
import me.kertf22.music_backend.model.UserModel;
import me.kertf22.music_backend.repositories.AudioRepository;
import me.kertf22.music_backend.repositories.ImageRepository;
import me.kertf22.music_backend.repositories.SongRepository;
import me.kertf22.music_backend.repositories.UserRepository;
import me.kertf22.music_backend.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final StorageService storageService;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    public SongController(StorageService storageService, SongRepository songRepository, UserRepository userRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Secured({})
    @GetMapping
    public ResponseEntity<List<DomainSong>> getSongs() {
        List<SongModel> songs = songRepository.findAll();

        List<DomainSong> mappedSongs = songs.stream().map(DomainSong::new).toList();

        return ResponseEntity.status(HttpStatus.OK).body(mappedSongs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DomainSong> getSong(@PathVariable(value = "id") String id) {
        Optional<SongModel> song = songRepository.findById(id);

        if (song.isEmpty()) {
            throw new CustomException("Song not found!", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new DomainSong(song.get()));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?>
    readSong(@PathVariable(value = "id") String id) {
        Optional<SongModel> song = songRepository.findById(id);

        if (song.isEmpty()) {
            throw new CustomException("Song not found!", HttpStatus.NOT_FOUND);
        }

        Resource data = storageService.loadAsResource(song.get().getAudio().getPath());

        SongModel songModel = song.get();
        songModel.setViews(songModel.getViews() + 1);
        songRepository.save(songModel);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + data.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }


    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<FileModel> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("file_data") UploadDTO file_data
    ) {

        if (file_data.storageType() == null) {
            throw new CustomException("Storage type not specified!", HttpStatus.BAD_REQUEST);
        }

        if (file_data.storageType() == StorageType.AUDIO) {
            var audioModel = storageService.storeAudio(file);

            audioRepository.save(audioModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(audioModel);

        } else {
            var imageModel = storageService.storeImage(file);

            imageRepository.save(imageModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(imageModel);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSong(
            @RequestBody @Valid SongRecordDTO songRecordDTO
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<UserModel> user = userRepository.findByUsername(auth.getName());

        if (user.isEmpty()) {
            throw new CustomException("User not found!", HttpStatus.NOT_FOUND);
        }

//        var songModel = new SongModel(
//                songRecordDTO.banner_image(),
//                songRecordDTO.audio_file(),
//                songRecordDTO.title(),
//                user.get(),
//                0,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                null
//        );

//        if (songRepository.existsSongByTitleEquals(songModel.getTitle())) {
//            throw new CustomException("Song with this title already exists!",HttpStatus.FORBIDDEN);
//        }
//
//        SongModel insertedSong = songRepository.save(songModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new DomainSong(insertedSong));
        return ResponseEntity.status(HttpStatus.CREATED).body("Not implemented yet!");
    }
}