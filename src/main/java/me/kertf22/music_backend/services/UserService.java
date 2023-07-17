package me.kertf22.music_backend.services;

import me.kertf22.music_backend.dtos.RegisterDTO;
import me.kertf22.music_backend.exceptions.CustomException;
import me.kertf22.music_backend.model.FollowModel;
import me.kertf22.music_backend.model.UserModel;
import me.kertf22.music_backend.repositories.FollowRepository;
import me.kertf22.music_backend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private FollowRepository followRepository;

    public void save(RegisterDTO data) {
        Optional<UserModel> existUserWithEmail = this.repository.findByEmail(data.email());

        if (existUserWithEmail.isEmpty()) {
            throw new CustomException("Email / Username already used!!", HttpStatus.BAD_REQUEST);
        }

        Optional<UserModel>  existUserWithUsername = this.repository.findByUsername(data.username());

        if (existUserWithUsername.isEmpty()) {
            throw new CustomException("Email / Username already used!!", HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserModel user = new UserModel();
        BeanUtils.copyProperties(data, user);
        user.setPassword(encryptedPassword);
        user.setPoints(0);

        this.repository.save(user);
    }


    public UserModel getUserProfile(String id) {

        Optional<UserModel> user = this.repository.findById(id);

        if(user.isEmpty()) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }

        return user.get();

    }

    public UserModel getUser(String username) {
        Optional<UserModel> user = this.repository.findByUsername(username);

        if(user.isEmpty()) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

    public String follow(String username, String followedId) {
        UserModel user = this.getUser(username);
        UserModel followed = this.getUserProfile(followedId);

        if(user.getId().equals(followed.getId())) {
            throw new CustomException("You can't follow yourself", HttpStatus.BAD_REQUEST);
        }

        Optional<FollowModel> followOptional = followRepository.findByFollowedIdAndFollowingId(followed.getId(), user.getId());

        if(followOptional.isPresent()) {
            FollowModel follow = followOptional.get();
            followRepository.delete(follow);
            return "Unfollowed user " + followed.getArtist_name() + "!";
        }

        FollowModel follow = new FollowModel(followed, user, LocalDateTime.now());
        followRepository.save(follow);
        return "Followed " + followed.getArtist_name() + "!";
    }

}
