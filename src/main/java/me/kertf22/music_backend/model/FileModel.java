package me.kertf22.music_backend.model;

import me.kertf22.music_backend.enums.ExtensionFile;
import me.kertf22.music_backend.enums.StorageType;

public interface FileModel {
    public String getId();
    public void setId(String id);
    public String getName();
    public void setName(String name);
    public String getPath();
    public void setPath(String path);
    public ExtensionFile getExtension();
    public void setExtension(ExtensionFile extension);
    public Integer getSize();
    public void setSize(Integer size);
    public StorageType getType();
    public void setType(StorageType type);
}
