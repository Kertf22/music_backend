package me.kertf22.music_backend.services.storage;

import jakarta.annotation.PostConstruct;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import me.kertf22.music_backend.enums.ExtensionFile;
import me.kertf22.music_backend.enums.StorageType;
import me.kertf22.music_backend.exceptions.CustomException;
import me.kertf22.music_backend.model.AudioModel;
import me.kertf22.music_backend.model.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import me.kertf22.music_backend.model.FileModel;
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
        var filename = this.validateFile(file, type);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CustomException("Failed to store file " + e.toString(), e);
        };

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
                throw new CustomException("File type not allowed - " +file.getContentType());
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

    public ImageModel storeImage(MultipartFile file) {
        String filename = this.store(file, StorageType.IMAGE);

        try {
            File newFile = new File(this.rootLocation.resolve(filename).toUri());
            BufferedImage bufferedImage = ImageIO.read(newFile);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            return new ImageModel(
                    UUID.randomUUID().toString(),
                    filename,
                    filename,
                    ExtensionFile.valueOf(StringUtils.getFilenameExtension(filename).toUpperCase()),
                    (int) file.getSize(),
                    width,
                    height
            );
        } catch (IOException e) {
            throw new CustomException("Failed to read file " + e.toString(), e);
        }

    }

    public AudioModel storeAudio(MultipartFile file) {
        String filename = this.store(file, StorageType.AUDIO);

        try {
            Header h = null;
            var newFile = new FileInputStream(String.valueOf(this.rootLocation.resolve(filename)));
            var bitstream = new Bitstream(newFile);
            h = bitstream.readFrame();
            // Get the audio format.

            long tn = newFile.getChannel().size();
            var duration = h.total_ms((int) tn)/1000;

            return new AudioModel(
                    UUID.randomUUID().toString(),
                    filename,
                    filename,
                    ExtensionFile.valueOf(StringUtils.getFilenameExtension(filename).toUpperCase()),
                    (int) file.getSize(),
                    duration
            );
        }catch (Exception e) {
            throw new CustomException("Failed to read file " + e.toString(), e);
        }

    }
}