package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WatchlistAddRequest {
 private    String movie_id;
 private String movie_title;
 private  Integer runtime;
 private Integer rating_val;

    public WatchlistAddRequest(String movie_id, String movie_title, Integer runtime) {

        this.movie_id = movie_id;
        this.movie_title=movie_title;
        this.runtime=runtime;

    }
    public WatchlistAddRequest(){

    }
    public WatchlistAddRequest(String movie_id, String movie_title, Integer runtime, Integer rating_val) {

        this.movie_id = movie_id;
        this.movie_title=movie_title;
        this.runtime=runtime;
        this.rating_val=rating_val;

    }



}
