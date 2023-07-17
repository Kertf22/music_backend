package me.kertf22.music_backend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "songs")
public class SongModel implements Serializable {
    private  static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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
    @Column(nullable = false, unique = true)
    private String banner_image;
    @Column(nullable = false, unique = true)
    private String audio_file;
    @Column(nullable = false)
    private String title;
    @ManyToOne(targetEntity = UserModel.class)
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    private UserModel artist;
    @Column(nullable = false)
    private Integer views;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updated_at;
    @Column()
    private LocalDateTime deleted_at;

    public SongModel() {
    }

    public SongModel(String banner_image, String audio_file, String title, UserModel artist, Integer views, LocalDateTime createdAt, LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.banner_image = banner_image;
        this.audio_file = audio_file;
        this.title = title;
        this.artist = artist;
        this.views = views;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public void setArtist(UserModel artist) {
        this.artist = artist;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserModel getArtist() {
        return artist;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}