package com.example.LargeScaleProject.Repository.Neo4jDB;

import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface Neo4jCommentRepository extends Neo4jRepository<Comment, Long> {
    @Query("MATCH (user:User)-[comment:COMMENT]->(post:Post {postId: $postId}) RETURN comment.text AS text, comment.order AS order LIMIT 5")
    List<Comment> findCommentsByPostId(@Param("postId") String postId);
    @Query("MATCH (p:Post{postId: $postId})<-[c:COMMENT]-(author:User) RETURN author.username")
    List <String>findAuthorByComments(@Param("postId") String postId);


}