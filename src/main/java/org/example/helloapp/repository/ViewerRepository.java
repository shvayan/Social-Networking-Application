package org.example.helloapp.repository;

import org.example.helloapp.models.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer,Long> {
    Optional<Viewer> findByViewerIdAndPostId(Long viewerId, Long postId);
}
