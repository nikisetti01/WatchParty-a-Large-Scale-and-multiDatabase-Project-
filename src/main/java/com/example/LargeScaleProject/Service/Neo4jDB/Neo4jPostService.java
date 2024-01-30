package com.example.LargeScaleProject.Service.Neo4jDB;

import com.example.LargeScaleProject.Exception.PostException;
import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommentRepository;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jPostRepository;
import org.springframework.web.servlet.tags.form.OptionsTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Neo4jPostService {
    @Autowired
    public Neo4jPostRepository postRepository;
    @Autowired
    public Neo4jCommunityService neo4jCommunityService;
    @Autowired
    public Neo4jUserRepository neo4jUserRepository;
    @Autowired
    private Neo4jCommentRepository neo4jCommentRepository;


    @Autowired
    public Neo4jPostService(Neo4jPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> createPost(Post post) {
        try {
            if (this.findById(post.getPostId()).isPresent())
                return Optional.empty();
            else
                return Optional.of(postRepository.save(post));

        } catch (Exception e) {
            throw new PostException("Error in creating a Post", e);
        }
    }

    public Optional<Post> findById(String postId) {
        try {

            return postRepository.findByPostId(postId);

        } catch (Exception e) {
            throw new PostException("Error in finding a Post", e);
        }
    }

    public boolean deletePost(String postId) {
        try {
            if (this.findById(postId).isEmpty())
                return false;
            else {
                postRepository.deleteByPostId(postId);
                return true;
            }

        } catch (Exception e) {
            throw new PostException("Error in finding a Post", e);
        }

    }

    public Optional<Post> belongrelationship(String postId, String name) {
        try {
            Optional<Community> communityOptional = neo4jCommunityService.findCommunityByName(name);
            Optional<Post> postOptional = this.findById(postId);
            if (communityOptional.isPresent() && postOptional.isPresent()) {
                Post post = postOptional.get();
                postRepository.createBelongRelationship(postOptional.get().getPostId(), communityOptional.get().getName());
                return Optional.of(post);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new PostException("Error connecting Post to a Community", e);
        }
    }

    public Optional<Community> findCommunity(String postId) {
        return Optional.of(postRepository.findCommunityOfPost(postId));
    }

    public Optional<String> findAuthorbypost(String postId) {
        String author = postRepository.findAuthor(postId);
        if (author != null) {
            return Optional.of(author);
        } else {
            return Optional.empty();
        }
    }
    public Post findPostbyTitle(String title){
        return postRepository.findByTitle(title);
    }

}
