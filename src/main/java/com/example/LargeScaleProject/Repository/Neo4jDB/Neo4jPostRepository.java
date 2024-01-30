package com.example.LargeScaleProject.Repository.Neo4jDB;
import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Neo4jPostRepository extends Neo4jRepository<Post,String> {
    // Puoi aggiungere eventuali metodi personalizzati qui se necessario
    Optional<Post>  findByPostId(String title);

    @Query("MATCH (p:Post {postId: $postId}) " +
            "MATCH (c:Community {name: $name}) " +
            "CREATE (p)-[:BELONG]->(c)")
    void createBelongRelationship(@Param("postId") String postId, @Param("name") String name);

    @Query("MATCH (p:Post {postId: $postId})-[:BELONG]->(c:Community) RETURN c.name as name")
    Community findCommunityOfPost(@Param("postId") String postId);
    @Query("MATCH (u:User)-[:POSTING]->(p:Post {postId: $postId}) RETURN "+
            "u.username as username")
     String findAuthor(@Param("postId") String postId);
   Post findByTitle(String Title);

    void deleteByPostId(String postId);
}
