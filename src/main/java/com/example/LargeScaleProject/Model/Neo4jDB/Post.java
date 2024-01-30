package com.example.LargeScaleProject.Model.Neo4jDB;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.util.UUID;

@NodeEntity ("Post")
@Getter
@Setter
@ToString
public class Post {
    @Id
    @Property("postId")
    private String postId;
    @Property("registerIndex")
    private String registerIndex;
    @Property("text")
    private String text;
    @Property("title")
    private String title;
    @Relationship(type = "COMMENT", direction = Relationship.OUTGOING)
    private Post commentPost;
    @Relationship(type = "POSTING", direction = Relationship.OUTGOING)
    private User Author;
    @Relationship(type = "BELONG", direction = Relationship.OUTGOING)
    private Community belongCommunity;
    // Costruttore vuoto
    public Post() {
    }

    // Costruttore con parametri
    public Post( String registerIndex, String text, String title) {
       this.postId= UUID.randomUUID().toString();
        this.registerIndex = registerIndex;
        this.text = text;
        this.title = title;

     }
    public Post( String postId,String registerIndex, String text, String title) {
        this.postId=postId;
        this.registerIndex = registerIndex;
        this.text = text;
        this.title = title;

    }
    }