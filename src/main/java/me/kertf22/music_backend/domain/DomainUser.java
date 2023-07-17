package me.kertf22.music_backend.domain;

import me.kertf22.music_backend.model.UserModel;

public class DomainUser {
    private String id;
    private String username;
    private String name;
    private String artist_name;
    private String email;
    private Integer points;

    public DomainUser(String id, String username, String name, String artist_name, String email, Integer points) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.artist_name = artist_name;
        this.email = email;
        this.points = points;
    }

    public DomainUser() {
    }

    public DomainUser(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.name = userModel.getName();
        this.artist_name = userModel.getArtist_name();
        this.email = userModel.getEmail();
        this.points = userModel.getPoints();
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setId(this.id);
        userModel.setUsername(this.username);
        userModel.setName(this.name);
        userModel.setArtist_name(this.artist_name);
        userModel.setEmail(this.email);
        userModel.setPoints(this.points);
        return userModel;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getEmail() {
        return email;
    }



    public Integer getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
