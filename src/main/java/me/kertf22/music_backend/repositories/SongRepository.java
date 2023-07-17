package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.SongModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SongRepository extends JpaRepository<SongModel, String> {

//    boolean existsSongByAudio_FileEquals(String audio_file);
//    boolean existsSongByBanner_ImageEquals(String banner_image);
    boolean existsSongByTitleEquals(String title);
}
