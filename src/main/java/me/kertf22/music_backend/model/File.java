package me.kertf22.music_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import me.kertf22.music_backend.enums.ExtensionFile;
import me.kertf22.music_backend.enums.StorageType;

public interface File {
    public String getId();
    public void setId(String id);
    public String getName();
    public void setName(String name);
    public String getPath();
    public void setPath(String path);
    public ExtensionFile getExtension();
    public void setExtension(ExtensionFile extension);
    public Number getSize();
    public void setSize(Number size);
    public StorageType getType();
    public void setType(StorageType type);
}
