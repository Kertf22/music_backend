package me.kertf22.music_backend.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Table(name = "users")
@Entity(name = "users")
public class UserModel implements UserDetails, Serializable {

    private  static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist_name;

    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer points;

    @OneToMany(mappedBy = "following")
    private Collection<FollowModel> followers;

    @OneToMany(mappedBy = "followed")
    private Collection<FollowModel> following;

    public Collection<FollowModel> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<FollowModel> followers) {
        this.followers = followers;
    }

    public Collection<FollowModel> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<FollowModel> following) {
        this.following = following;
    }

    public void addFollower(FollowModel followModel){
        this.followers.add(followModel);
    }

    public void addFollowing(FollowModel followModel){
        this.following.add(followModel);
    }

    public void removeFollower(FollowModel followModel){
        this.followers.remove(followModel);
    }

    public void removeFollowing(FollowModel followModel){
        this.following.remove(followModel);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
