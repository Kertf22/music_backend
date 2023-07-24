package me.kertf22.music_backend.services.storage;

import jakarta.annotation.PostConstruct;
import me.kertf22.music_backend.enums.StorageType;
import me.kertf22.music_backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
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
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.AffineTransform;

@Service
public class FileSystemStorageService implements StorageService {
    String[] allowedMimeTypes = {"audio/mpeg", "audio/mp4", "audio/wav", "audio/flac"};
    String[] imagesMimeTypes = {"image/png", "image/jpeg"};
    String imageLocation = "image";
    String audioLocation = "audio";
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
            throw new CustomException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, StorageType type) {

        var filename = validateFile(file, type);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CustomException("Failed to store file " + e.toString(), e);
        }


        if(type == StorageType.IMAGE) {
            File newFile = new File(this.rootLocation.resolve(filename).toUri());

            BufferedImage img = null;
            try { img = ImageIO.read(newFile); }
            catch (IOException e) { e.printStackTrace(System.out); }
                    System.out.println("  Image read.");
            if (img != null) {

               var image =  img.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
                System.out.println("  Image resize.");
                img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                img.getGraphics().drawImage(image, 0, 0, null);
                System.out.println("  Image draw.");
//                try {
//                    Files.createFile(this.rootLocation.resolve(filename));
//                    Files.write(this.rootLocation.resolve(filename), ((DataBufferByte) img.getData().getDataBuffer()).getData());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                try { ImageIO.write(img, "png", newFile); }
                catch (IOException e) { e.printStackTrace(System.out); }
                System.out.println("  Image writed.");
            }

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
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new CustomException("Could not read file: " + filename, e);
        }
    }

    @Override
    public String validateFile(MultipartFile file, StorageType type) {

        if (file.isEmpty()) {
            throw new CustomException("Failed to store empty file ");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        filename = UUID.randomUUID().toString() + "." + extension;

        if (filename.contains("..")) {
            // This is a security check
            throw new CustomException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }

        if (type == StorageType.AUDIO) {
            if (!Arrays.asList(allowedMimeTypes).contains(file.getContentType())) {
                throw new CustomException("File type not allowed");
            }

            filename = audioLocation + "/" + filename;

        } else if (type == StorageType.IMAGE) {

            if (!Arrays.asList(imagesMimeTypes).contains(file.getContentType())) {
                throw new CustomException("File type not allowed");
            }


            filename = imageLocation + "/" + filename;
        } else {
            throw new CustomException("File type not allowed");
        }

        return filename;
    }


    // scale a grayscale image
//    public static BufferedImage resize(BufferedImage img, int newHeight) {
//
//
//
//    }
}