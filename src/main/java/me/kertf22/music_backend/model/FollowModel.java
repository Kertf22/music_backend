package me.kertf22.music_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class FollowModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "followed_user_id")
    private UserModel followed;
    @ManyToOne()
    @JoinColumn(name = "following_user_id")
    private UserModel following;

    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime deleted_at;

    public FollowModel(UserModel followed, UserModel following,LocalDateTime created_at) {
        this.followed = followed;
        this.following = following;
        this.created_at = created_at;
    }

    public FollowModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getFollowed() {
        return followed;
    }

    public void setFollowed(UserModel followed) {
        this.followed = followed;
    }

    public UserModel getFollowing() {
        return following;
    }

    public void setFollowing(UserModel following) {
        this.following = following;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }
}
