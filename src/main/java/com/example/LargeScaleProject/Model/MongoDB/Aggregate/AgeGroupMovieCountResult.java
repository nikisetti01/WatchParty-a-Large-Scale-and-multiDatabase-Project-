package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AgeGroupMovieCountResult {
    private String ageGroup;
    private Long movieCount;

    // Costruttore
    public AgeGroupMovieCountResult(String ageGroup, Long movieCount) {
        this.ageGroup = ageGroup;
        this.movieCount = movieCount;
    }

    // Getter e setter
    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Long getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(Long movieCount) {
        this.movieCount = movieCount;
    }

    // Override del metodo toString (opzionale, per scopi di debugging)
    @Override
    public String toString() {
        return "AgeGroupMovieCountResult{" +
                "ageGroup='" + ageGroup + '\'' +
                ", movieCount=" + movieCount +
                '}';
    }
    public static void sortByAge(List<AgeGroupMovieCountResult> results) {
        Collections.sort(results, Comparator.comparing(AgeGroupMovieCountResult::getAgeGroup));
    }
}
