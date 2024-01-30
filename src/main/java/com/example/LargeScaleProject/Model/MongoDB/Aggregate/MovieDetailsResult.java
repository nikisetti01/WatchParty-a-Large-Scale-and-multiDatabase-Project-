package com.example.LargeScaleProject.Model.MongoDB.Aggregate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class MovieDetailsResult {

    private String _id;
    private List<String> user;
    private double avgRating;

    public MovieDetailsResult(String _id, List<String> user, double avgRating) {
        this._id = _id;
        this.user = user;
        this.avgRating = avgRating;
    }

    public MovieDetailsResult() {
    }

}
