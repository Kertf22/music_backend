package me.kertf22.music_backend.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);


    Path load(String filename);

    Resource loadAsResource(String filename);

//    void deleteAll();

}