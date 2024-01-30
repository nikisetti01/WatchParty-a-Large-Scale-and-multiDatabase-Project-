package com.example.LargeScaleProject.Model.MongoDB;

import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class WatchlistItem {
    @Setter
    @Getter
    private Movie movie;
    @Getter
    @Setter
    private Integer watch;
    @Getter
    @Setter
    private Review review;


    public WatchlistItem() {

    }
    public WatchlistItem(Movie movie) {
        this.movie = movie;
    }

    public WatchlistItem(Movie movie, Review review, Integer watch) {
        this.movie = movie;
        this.review = review;
        this.watch = watch;

    }
    public WatchlistItem(Movie movie, Integer watch) {
        this.movie = movie;
        this.watch = watch;

    }

    @Override
    public String toString() {
        return "{" +
                "movie=" + movie.getMovie_title() +
                ", watch=" + watch +
                ", " + (review != null ? "rating_val " + review.getRating_val() : " ") +
                '}';
    }
}
