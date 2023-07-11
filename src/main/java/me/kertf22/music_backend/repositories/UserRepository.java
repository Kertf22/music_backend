package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.SongModel;
import me.kertf22.music_backend.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<UserModel, String> {

    UserDetails findByEmail(String email);

    UserDetails findByUsername(String username);
}
