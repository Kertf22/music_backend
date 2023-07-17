package me.kertf22.music_backend.domain;

import me.kertf22.music_backend.model.SongModel;

import java.time.LocalDateTime;

public class DomainSong {
    private String id;
    private String banner_image;
    private String audio_file;
    private String title;
    private DomainUser artist;
    private Integer views;
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;


    public DomainSong() {
    }

    public DomainSong(String id, String banner_image, String audio_file, String title, DomainUser artist, Integer views, LocalDateTime createdAt, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.id = id;
        this.banner_image = banner_image;
        this.audio_file = audio_file;
        this.title = title;
        this.artist = artist;
        this.views = views;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public DomainSong(SongModel songModel){
        this.id = songModel.getId();
        this.banner_image = songModel.getBanner_image();
        this.audio_file = songModel.getAudio_file();
        this.title = songModel.getTitle();
        this.artist = new DomainUser(songModel.getArtist());
        this.views = songModel.getViews();
        this.createdAt = songModel.getCreatedAt();
        this.updated_at = songModel.getUpdated_at();
        this.deleted_at = songModel.getDeleted_at();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getAudio_file() {
        return audio_file;
    }

    public void setAudio_file(String audio_file) {
        this.audio_file = audio_file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DomainUser getArtist() {
        return artist;
    }

    public void setArtist(DomainUser artist) {
        this.artist = artist;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }
}
