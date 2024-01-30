package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Document(collection = "review")

public class ReviewAggregateResult {

    private String _id;
    private long totalReviews;
    private double avgRating;

    public ReviewAggregateResult(String _id, long totalReviews, double avgRating) {
        this._id = _id ; //movie_id
        this.totalReviews = totalReviews;
        this.avgRating = avgRating;
    }
    public ReviewAggregateResult() {
        // Costruttore vuoto necessario per la deserializzazione
    }

}
