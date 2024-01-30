package com.example.LargeScaleProject.Model.Neo4jDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@RelationshipProperties
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Post endNode;
    private String text;
    private Integer order;


    public Comment() {
    }
    public Comment(String text) {
        this.text=text;
    }

    public Comment(String text, Integer order) {
        this.text = text;
        this.order = order;
    }
    public String toString() {
        return "Neo4jCommunity{" +

                ", text='" + text + '\'' +
                ",order="+order+
                "nel Post "+ endNode+
                '}';
    }
}


