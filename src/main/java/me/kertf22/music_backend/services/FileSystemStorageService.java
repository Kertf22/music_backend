package me.kertf22.music_backend.services;

import jakarta.annotation.PostConstruct;
import me.kertf22.music_backend.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    String[] allowedMimeTypes = {"audio/mpeg","audio/mp4","audio/wav","audio/flac"};
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService() {
        this.rootLocation = Path.of("uploads");
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        filename = UUID.randomUUID().toString() + "." + extension;

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }

            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            // check mime type only accept mp3

            if (!Arrays.asList(allowedMimeTypes).contains(file.getContentType())) {
                System.out.println(file.getContentType());
                throw new StorageException("File type not allowed");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new FileNotFoundException(
                    "Could not read file: " + filename);
        }
        catch (MalformedURLException | FileNotFoundException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

}