package me.kertf22.music_backend.services.storage;

import me.kertf22.music_backend.enums.StorageType;
import me.kertf22.music_backend.model.AudioModel;
import me.kertf22.music_backend.model.FileModel;
import me.kertf22.music_backend.model.ImageModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {

    String[] types = {"audio", "image"};
    void init();

    String store(MultipartFile file, StorageType type);

    ImageModel storeImage(MultipartFile file);

    AudioModel storeAudio(MultipartFile file);
    Path load(String filename);
    Resource loadAsResource(String filename);

    String validateFile(MultipartFile file, StorageType type);

}