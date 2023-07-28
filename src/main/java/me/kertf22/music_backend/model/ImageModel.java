package me.kertf22.music_backend.model;

import jakarta.persistence.*;
import me.kertf22.music_backend.enums.ExtensionFile;
import me.kertf22.music_backend.enums.StorageType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "image")
@Table(name = "image")
public class ImageModel implements Serializable, FileModel {
    @Serial
    private  static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private ExtensionFile extension;
    @Column(nullable = false)
    private Integer size;
    @Column(nullable = false)
    private StorageType type;
    @Column(nullable = false)
    private Integer width;
    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column()
    private LocalDateTime deletedAt;

    public ImageModel(String id, String name, String path, ExtensionFile extension, Integer size, Integer width, Integer height) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.width = width;
        this.height = height;
        this.type = StorageType.IMAGE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public ImageModel() {
        super();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public ExtensionFile getExtension() {
        return extension;
    }

    @Override
    public void setExtension(ExtensionFile extension) {
        this.extension = extension;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    @Override
    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public StorageType getType() {
        return type;
    }

    @Override
    public void setType(StorageType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
