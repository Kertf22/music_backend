package me.kertf22.music_backend.model;

import jakarta.persistence.*;
import me.kertf22.music_backend.enums.ExtensionFile;
import me.kertf22.music_backend.enums.StorageType;


@Entity
@Table(name = "image")
public class ImageModel implements File {
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
    private Number size;
    @Column(nullable = false)
    private StorageType type;
    @Column(nullable = false)
    private Number width;
    @Column(nullable = false)
    private Number height;

    public ImageModel(String id, String name, String path, ExtensionFile extension, Number size, Number width, Number height) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.width = width;
        this.height = height;
    }

    public ImageModel() {
        super();
    }

    public Number getHeight() {
        return height;
    }

    public void setHeight(Number height) {
        this.height = height;
    }

    public Number getWidth() {
        return width;
    }

    public void setWidth(Number width) {
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
    public Number getSize() {
        return size;
    }

    @Override
    public void setSize(Number size) {
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
}
