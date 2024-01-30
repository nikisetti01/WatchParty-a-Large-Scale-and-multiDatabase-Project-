package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CommentRequest {
 private    String text;
  private   Integer order;
  private String author;

public CommentRequest(){

}
public CommentRequest(String text,String author){
    this.text = text;
    this.author = author;
}
    public CommentRequest(String text, Integer order, String author) {
        this.text = text;
        this.order = order;
        this.author = author;
    }

}
