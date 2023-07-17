package me.kertf22.music_backend.repositories;

import me.kertf22.music_backend.model.FollowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowModel, String> {

    Optional<FollowModel> findByFollowedIdAndFollowingId(String followedId, String followingId);

    List<FollowModel> findAllByFollowedId(String followedId);

    List<FollowModel> findAllByFollowingId(String followingId);

    void deleteByFollowedIdAndFollowingId(String followedId, String followingId);
}
