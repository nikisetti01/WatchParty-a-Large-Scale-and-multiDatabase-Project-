package com.example.LargeScaleProject.Model.MongoDB.Aggregate;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class MovieRandomReviewsResult {

    private String movieId;
    private List<Review> randomReviews;

    public MovieRandomReviewsResult(String movieId, List<Review> randomReviews) {
        this.movieId = movieId;
        this.randomReviews = randomReviews;
    }

    public MovieRandomReviewsResult() {
    }

}
