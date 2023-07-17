package me.kertf22.music_backend.services;

import me.kertf22.music_backend.enums.StorageType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {

    String[] types = {"audio", "image"};
    void init();

    String store(MultipartFile file, StorageType type);


    Path load(String filename);

    Resource loadAsResource(String filename);

//    void deleteAll();

}