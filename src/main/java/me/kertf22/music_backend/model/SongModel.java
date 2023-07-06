package me.kertf22.music_backend.model;

import jakarta.persistence.Entity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SongModel {

    @UUID
    private String id;

    private String fileName;

    private String title;
    private String artist;
    private boolean isFavorited;
}