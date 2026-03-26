package org.example.helloapp.controller.api.v1;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.HttpResponseDto;
import org.example.helloapp.dto.PostFilterRequest;
import org.example.helloapp.dto.PostRequestDto;
import org.example.helloapp.models.Post;
import org.example.helloapp.service.IFileStorageService;
import org.example.helloapp.service.IPostService;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {


    private final IPostService postService;


    private final IFileStorageService fileStorageService;

     // Implement endpoints for creating, deleting, and fetching posts
    @PostMapping(value = "/create",consumes = "multipart/form-data")
    public ResponseEntity<HttpResponseDto> createPost(@Valid @ModelAttribute PostRequestDto requestDto) {

        HttpResponseDto response = new HttpResponseDto();
        String mediaUrl;
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            if (requestDto.getAttachment() == null || requestDto.getAttachment().isEmpty()) {
                mediaUrl = ""; // No media attached
            }else{
                mediaUrl = fileStorageService.storeFile(requestDto.getAttachment(),"post");
            }

            postService.createPost(requestDto.getContent(), mediaUrl, userId);
            response.setMessage("Posts crate successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(null);
        } catch (IOException | RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<HttpResponseDto> deletePost(@PathVariable Long postId) {
        HttpResponseDto response = new HttpResponseDto();
        // Implementation for deleting a post
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            // Example: Delete a post by ID (replace with actual post ID)
            postService.deletePost(postId, userId);
            response.setMessage("Post deleted successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(null);

        } catch (RuntimeException e) {
            // Handle exceptions and return appropriate response
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPosts")
    public ResponseEntity<HttpResponseDto> getPosts(@RequestBody PostFilterRequest request) {
        // Implementation for fetching posts
        HttpResponseDto response = new HttpResponseDto();
        try {


            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            // Example: Fetch posts for a user (replace with actual user ID and pagination parameters)
            Page<Post> posts = postService.getPostsByUserId(request.getSearch(),userId, request.getPage(), request.getSize());
            response.setMessage("Posts fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(posts);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/like/{postId}")
    public ResponseEntity<HttpResponseDto> likePost(@PathVariable Long postId) {
        HttpResponseDto response = new HttpResponseDto();
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            postService.likePost(postId, userId);
            response.setMessage("Post liked successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(null);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/unlike/{postId}")
    public ResponseEntity<HttpResponseDto> unlikePost(@PathVariable Long postId) {
        HttpResponseDto response = new HttpResponseDto();
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            postService.unlikePost(postId, userId);
            response.setMessage("Post unliked successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(null);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getDetiles/{postId}")
    public ResponseEntity<HttpResponseDto> getPostById(@PathVariable Long postId) {
        HttpResponseDto response = new HttpResponseDto();
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            Post post = postService.getPostById(userId,postId);
            response.setMessage("Post fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(post);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPostsTest")
    public ResponseEntity<HttpResponseDto> getPostsTest(@RequestBody PostFilterRequest request) {
        // Implementation for fetching posts
        HttpResponseDto response = new HttpResponseDto();
        try {


            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            // Example: Fetch posts for a user (replace with actual user ID and pagination parameters)
            Page<Post> posts = postService.getAllPosts(request.getSearch(), request.getPage(), request.getSize());
            response.setMessage("Posts fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(posts);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
