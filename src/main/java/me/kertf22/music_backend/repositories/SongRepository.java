package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.SongModel;
import org.springframework.data.jpa.repository.JpaRepository;

public class SongRepository extends JpaRepository<SongRepository, SongModel> {
}
