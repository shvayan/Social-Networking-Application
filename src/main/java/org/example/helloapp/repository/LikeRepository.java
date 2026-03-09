package org.example.helloapp.repository;

import org.example.helloapp.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByPostIdAndViewerId(Long postId, Long viewerId);
}
