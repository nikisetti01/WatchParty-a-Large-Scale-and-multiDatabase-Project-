package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RatingCountResult {

    private Integer _id;
    private long count;

    public RatingCountResult(Integer _id, long count) {
        this._id = _id;
        this.count = count;
    }

    public RatingCountResult() {
    }

}