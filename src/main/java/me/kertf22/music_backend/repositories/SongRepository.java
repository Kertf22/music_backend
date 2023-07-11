package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.SongModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SongRepository extends MongoRepository<SongModel, String> {

    boolean existsSongByFileNameEquals(String fileName);

    boolean existsSongByTitleEquals(String title);
}
