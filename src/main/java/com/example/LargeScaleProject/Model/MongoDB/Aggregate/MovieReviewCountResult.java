package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieReviewCountResult {

    private String _id;
    private long totalReviews;

    public MovieReviewCountResult(String _id, long totalReviews) {
        this._id = _id;
        this.totalReviews = totalReviews;
    }

    public MovieReviewCountResult() {
    }

}