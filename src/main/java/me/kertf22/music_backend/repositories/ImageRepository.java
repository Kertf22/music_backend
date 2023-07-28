package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, String> {

}
