package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.AudioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<AudioModel, String> {
}
