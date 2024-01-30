package com.example.LargeScaleProject.Model.Neo4jDB;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.annotation.*;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import org.springframework.data.repository.query.Param;

import java.util.*;
@NodeEntity (label = "User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Property("display_name")
    private String display_name;
    @Property("username")
    private String username;

    // Relazione POSTED tra User e Post
    @Relationship(type = "POSTING", direction = Direction.OUTGOING)
    private Set<Post> postedPosts;

    // Relazione COMMENT tra User e Post
    @Relationship(type = "COMMENT", direction = Direction.OUTGOING)
    private Set<Comment> commentedPosts;

    // Relazione JOIN tra User e Community
    @Relationship(type = "JOIN", direction = Direction.OUTGOING)
    private Set<Community> joinedCommunities;

    public User() {
        // Costruttore vuoto richiesto da Neo4j OGM
    }
    public User(String username){
        this.username=username;
    }

    public User(String username, String displayName) {
       this.id=UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.username = username;
        this.display_name = displayName;
        // Puoi inizializzare altri campi se necessario
    }
    public void joinCommunity(Community community) {
        if (joinedCommunities == null) {
            joinedCommunities = new HashSet<>();
        }
        joinedCommunities.add(community);
    }
    public void addComment(Comment comment){
        commentedPosts.add(comment);
    }
    @Override
    public String toString() {
        return "Neo4jUser{" +
                "id"+ id +
                ", display_name='" + display_name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}