package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
@ToString

public class ReviewRequest {
    String authorusername;
    Integer rating_val;
    String review;
    String movie_id;
    String movie_title;

    public ReviewRequest(String authorusername, Integer rating_val, String review) {
        this.authorusername = authorusername;
        this.rating_val = rating_val;
        this.review = review;


    }
    public ReviewRequest(){

    }
    public ReviewRequest(String authorusername, Integer rating_val, String review, String movie_id, String movie_title) {
        this.authorusername = authorusername;
        this.rating_val = rating_val;
        this.review = review;
        this.movie_id=movie_id;
        this.movie_title=movie_title;
    }
}
