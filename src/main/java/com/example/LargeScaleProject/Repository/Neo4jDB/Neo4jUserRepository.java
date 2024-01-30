package com.example.LargeScaleProject.Repository.Neo4jDB;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
// chiedere sto change a matte domani

@Repository
public interface Neo4jUserRepository extends Neo4jRepository<User, Long> {
    // Puoi aggiungere eventuali metodi personalizzati qui se necessario
    Optional <User> findByUsername(String username);
    void deleteByUsername(String username);
    @Query("MATCH (u:User {username: $username})" +
            "MATCH (c:Community {name: $communityName}) " +
            "CREATE (u)-[:JOIN]->(c)")
    void joinCommunity(@Param("username") String username, @Param("communityName") String communityName);
    @Query("MATCH (u:User {username: $username})" +
            "MATCH (p:Post {postId: $postId}) " +
            "CREATE (u)-[:POSTING]->(p)")
    void postInUser(@Param("username") String username, @Param("postId") String postId);
    // restituisce tutte le relazioni
    @Query("MATCH (u:User {username: $username})-[:JOIN]->(c:Community) RETURN c.name as name")
    List<Community> findAllCommunitiesByUsername(@Param("username") String username);
    @Query("MATCH (u:User {username: $username})-[:POSTING]->(p:Post) RETURN p.postId AS postId,p.registerIndex as registerIndex," +
            "p.text as text, p.title as title")
    List<Post> findAllPostsByUsername(@Param("username") String username);
    // reccomend a random community of the most active user
    @Query("MATCH (selectedUser:User {username: $selectedUsername})-[:POSTING|COMMENT]->(activity:Post) " +
            "WITH selectedUser, activity " +
            "MATCH (otherUser:User)-[:POSTING|COMMENT]->(activity) " +
            "WHERE otherUser <> selectedUser " +
            "WITH otherUser, COUNT(activity) AS activityCount " +
            "ORDER BY activityCount DESC " +
            "LIMIT 1 " +
            "MATCH (userA:User {username: otherUser.username})-[:JOIN]->(communityA:Community) " +
            "WHERE NOT EXISTS { " +
            "  MATCH (userA)-[:JOIN]->(communityA)<-[:JOIN]-(userB:User {username: $selectedUsername}) " +
            "} " +
            "RETURN otherUser.username AS mostActiveUser, activityCount, COLLECT(communityA.name)[0] AS comname")
    RecommendedCommunity getMostActiveUserAndCommunity(@Param("selectedUsername") String selectedUsername);

    @Query("MATCH p = shortestPath((u1:User{username:$username1})-[:POSTING|BELONG|COMMENT*..50]-(u2:User{username:$username2})) " +
            "RETURN length(p) as pathdistance")
    Integer findShortestPath(@Param("username1") String username1, @Param("username2") String  username2);


}


