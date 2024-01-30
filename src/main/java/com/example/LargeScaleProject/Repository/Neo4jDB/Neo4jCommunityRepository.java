package com.example.LargeScaleProject.Repository.Neo4jDB;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.UserActivity;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Neo4jCommunityRepository extends Neo4jRepository<Community, String> {

    // Puoi aggiungere eventuali metodi personalizzati qui se necessario
    @Query("MATCH (c:Community {name: $name}) RETURN c")
    Optional<Community> findByName( @Param("name") String name);
    @Query("MATCH (p:Post)-[:BELONG]->(c:Community {name: $name}) RETURN p.postId as postId, p.registerIndex as registerIndex, p.text as text, p.title as title  LIMIT 10")
    List<Post> findPostsByCommunityName(@Param("name") String name);

    @Query("MATCH (u:User)-[activity:POSTING|COMMENT]->(p:Post)-[:BELONG]->(c:Community {name: $communityname}) " +
            "RETURN u.username AS username, " +
            "COUNT(CASE WHEN TYPE(activity) = 'POSTING' THEN p END) AS numPostings, " +
            "COUNT(CASE WHEN TYPE(activity) = 'COMMENT' THEN p END) AS numComments, " +
            "COUNT(p) AS totalActivity " +
            "ORDER BY totalActivity DESC " +
            "LIMIT $limit")
    List<UserActivity> findMostActiveUsersInCommunity(@Param("communityname") String communityName,@Param("limit") int  limit);



}
