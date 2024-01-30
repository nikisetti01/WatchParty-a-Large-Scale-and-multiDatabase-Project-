package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWatchTimeResult {
    private Integer totalRuntime;

    public UserWatchTimeResult() {
    }

    public UserWatchTimeResult(Integer totalRuntime) {
        this.totalRuntime = totalRuntime;
    }

    public Integer getTotalRuntime() {
        return totalRuntime;
    }

    public void setTotalRuntime(Integer totalRuntime) {
        this.totalRuntime = totalRuntime;
    }
    @Override
    public String toString() {
        return "UserWatchTimeResult: " +
                "totalRuntime = " + totalRuntime ;
    }

}
