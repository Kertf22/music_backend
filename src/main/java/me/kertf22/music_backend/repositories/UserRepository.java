package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.SongModel;
import me.kertf22.music_backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserDetails findByEmail(String email);

    UserDetails findByUsername(String username);
}
