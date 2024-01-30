package com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RecommendedCommunity {
    private    String mostActiveUser;
    private     Integer activityCount;
    private     String comname;
    // "RETURN otherUser.username AS mostActiveUser, activityCount, COLLECT(communityA.name)[0] AS ")
    public RecommendedCommunity(String mostActiveUser, Integer activityCount, String comname) {
        this.mostActiveUser = mostActiveUser;
        this.activityCount= activityCount;
        this.comname = comname;
    }
}
