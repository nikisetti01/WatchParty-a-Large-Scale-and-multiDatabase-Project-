package com.example.LargeScaleProject.Model.httpRequest;

import com.example.LargeScaleProject.Model.MongoDB.Aggregate.MovieRandomReviewsResult;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Topfilmrequest {
 private   String movie_id;
 private double avgRating;
 private long totalReviews;
 private List<MovieRandomReviewsResult> topReviews;

    public Topfilmrequest(String movie_id, double avgRating, long totalReviews, List<MovieRandomReviewsResult> topReviews) {
        this.movie_id = movie_id;
        this.avgRating = avgRating;
        this.totalReviews = totalReviews;
        this.topReviews = topReviews;
    }



}
