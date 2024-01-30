package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MostWatchedMovieResult {
    private Long userCount;

    // Costruttore, getter e setter

    public MostWatchedMovieResult() {
    }

    public MostWatchedMovieResult(Long userCount) {
        this.userCount = userCount;
    }

}