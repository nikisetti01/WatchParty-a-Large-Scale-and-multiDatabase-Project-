package com.example.LargeScaleProject.Model.Neo4jDB;

import lombok.Getter;
import lombok.Setter;

import org.neo4j.cypherdsl.core.Use;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.repository.query.Param;

import java.util.Set;
@Getter
@Setter

@NodeEntity("Community")
public class Community {


    @Id
    private String name;
    @Relationship(type = "JOIN", direction = "INCOMING")
    private Set<User> joinedUsers;
    @Relationship(type = "BELONG", direction = "INCOMING")
    private Set<Post> belongPosts;



    // Costruttore vuoto
    public Community() {

    }

    // Costruttore con parametri
    public Community(String name) {
        this.name = name;
    }
    public String toString() {
        return "Neo4jCommunity{" +

                ", name='" + name + '\'' +
                '}';
    }
}
