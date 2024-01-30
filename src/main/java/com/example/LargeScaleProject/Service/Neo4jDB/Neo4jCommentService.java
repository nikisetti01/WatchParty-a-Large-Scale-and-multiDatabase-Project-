package com.example.LargeScaleProject.Service.Neo4jDB;

import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommentRepository;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class Neo4jCommentService {
    @Autowired
    Neo4jUserService neo4jUserService;
    @Autowired
    Neo4jPostService neo4jPostService;

    private final Neo4jCommentRepository commentRepository;

    @Autowired
    public Neo4jCommentService(Neo4jCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public List <Comment> Postcomments(String postId){ //trova commenti associati ad un post
        return commentRepository.findCommentsByPostId(postId);
    }
    public Optional <Comment> createComment(Comment comment, String username, String postId ) { //crea il commento e lo associa al post
        Optional <User> userOptional=neo4jUserService.getUserByUsername(username);
        Optional <Post> postOptional=neo4jPostService.findById(postId);
        if(userOptional.isPresent() && postOptional.isPresent()) {
           List<Comment> comments=commentRepository.findCommentsByPostId(postOptional.get().getPostId());
           comment.setEndNode(postOptional.get());
           userOptional.get().addComment(comment);
           neo4jUserService.updateUser(userOptional.get());
        return Optional.of(commentRepository.save(comment));
        } else
            return Optional.empty();
    }
    public List<String> findauthors(String postId){
        return commentRepository.findAuthorByComments(postId);
    }
}





