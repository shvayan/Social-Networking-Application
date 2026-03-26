package org.example.helloapp.service;

import org.example.helloapp.models.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPostService {
    void createPost(String description, String mediaUrl,  Long userId);

    void deletePost(Long postId, Long userId);

    Page<Post> getPostsByUserId(String query,Long userId, int page, int size);

    void likePost(Long postId, Long userId);

    void unlikePost(Long postId, Long userId);



    Post getPostById(Long userId ,Long postId);

    Page<Post> getAllPosts(String query, int page, int size);

}
