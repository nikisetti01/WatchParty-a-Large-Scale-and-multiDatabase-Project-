package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class PostRequest {
private    String author;
 private   String postId;
 private    String title;
  private   String text;
    private    List Comment;


    public PostRequest(String author, String postId, String title, String text, List comment) {
        this.author = author;
        this.postId = postId;
        this.title = title;
        this.text = text;
        Comment = comment;
    }
    public PostRequest(){

    }
    public PostRequest(String author, String title, String text) {
        this.author = author;
        this.postId= UUID.randomUUID().toString();
        this.title = title;
        this.text = text;

    }


}
