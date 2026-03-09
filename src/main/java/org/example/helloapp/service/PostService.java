package org.example.helloapp.service;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.exception.UnauthorisedException;
import org.example.helloapp.models.*;
import org.example.helloapp.repository.*;

import org.example.helloapp.util.MediaHelper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class PostService implements IPostService{


    private final UserRepository userRepository;


    private final PostRepository postRepository;


    private final LikeRepository likeRepository;


    private final AttachmentRepository attachmentRepository;


    private final ViewerRepository viewerRepository;

    @Override
    public void createPost(String description, String mediaUrl, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));


        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() +  24 * 60 * 60 * 1000);



        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);
        post.setStart_time(startDate);
        post.setEnd_time(endDate);
        post.setCreated_by(userId);
        post = this.postRepository.save(post);

        if(mediaUrl != null){
            FileType fileType = MediaHelper.getMediaType(mediaUrl);
            Attachment attachment = new Attachment();
            attachment.setAttachmentType(AttachmentType.POST);
            attachment.setFileType(fileType);
            attachment.setFileUrl(mediaUrl);
            attachment.setCreated_by(userId);
            attachment.setPost(post);
            this.attachmentRepository.save(attachment);
        }

    }

    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if(!post.getUser().getId().equals(userId)){
            throw new UnauthorisedException("Unauthorized");
        }
        List<Attachment> attachments = post.getAttachments();

        this.attachmentRepository.deleteAll(attachments);
        this.postRepository.delete(post);
    }

    @Override
    public Page<Post> getPostsByUserId(Long userId, int page, int size) {
        Pageable pageable;

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = this.postRepository.findAll(pageable);


        for(Post post : posts){
            User user = post.getUser();

            post.setUsername(user.getUsername());
            post.setImageUrl(user.getImageUrl() != null ? user.getImageUrl() : null);


        }

        return posts;
    }

    @Override
    public void likePost(Long postId, Long userId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> list = this.likeRepository.findByPostIdAndViewerId(postId, userId);

        if(list.isPresent()){
            Like like = list.get();

            if(like.getVoteType().equals(VoteType.DOWNVOTE)){


                like.setVoteType(VoteType.UPVOTE);
                like.setUpdated_at(new Date());
                like.setUpdated_by(userId);
                this.likeRepository.save(like);
            }else{
                this.likeRepository.delete(like);
            }


        }else {
            Like like = new Like();
            like.setPost(post);
            like.setViewer(user);
            like.setVoteType(VoteType.UPVOTE);
            like.setCreated_by(userId);
            like.setCreated_at(new Date());
            this.likeRepository.save(like);

        }
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> list = this.likeRepository.findByPostIdAndViewerId(postId, userId);

        if(list.isPresent()){
            Like like = list.get();
            if(like.getVoteType().equals(VoteType.UPVOTE)){

                like.setVoteType(VoteType.DOWNVOTE);
                like.setUpdated_at(new Date());
                like.setUpdated_by(userId);
                this.likeRepository.save(like);
            }else{
                this.likeRepository.delete(like);
            }

        }else {
            Like like = new Like();
            like.setPost(post);
            like.setViewer(user);
            like.setVoteType(VoteType.DOWNVOTE);
            like.setCreated_by(userId);
            like.setCreated_at(new Date());
            this.likeRepository.save(like);
        }

    }



    @Override
    public Post getPostById(Long userId ,Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = post.getUser();
        post.setUsername(user.getUsername());
        post.setImageUrl(user.getImageUrl() != null ? user.getImageUrl() : null);


        User loginUser = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Viewer> viewerOptional = this.viewerRepository.findByViewerIdAndPostId(userId, postId);

        if(viewerOptional.isEmpty()){
            Viewer viewer = new Viewer();
            viewer.setPost(post);
            viewer.setViewer(loginUser);
            viewer.setCreated_by(userId);
            viewer.setCreated_at(new Date());
            this.viewerRepository.save(viewer);
        }

        return post;
    }

}
