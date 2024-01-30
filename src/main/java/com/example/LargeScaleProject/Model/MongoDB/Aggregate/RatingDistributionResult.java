package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDistributionResult {
    private Integer _id;
    private long count;

    public RatingDistributionResult(Integer _id, long count) {
        this._id = _id;
        this.count = count;
    }

    public RatingDistributionResult() {
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
