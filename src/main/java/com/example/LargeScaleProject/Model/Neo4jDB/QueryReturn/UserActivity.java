package com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserActivity {
 private    String username;
private    Integer numPostings;
private     Integer numComments;
private     Integer totalActivity;
 public    UserActivity(String username, Integer numPostings, Integer numComments, Integer totalActivity){
     this.username=username;
     this.numPostings=numPostings;
     this.numComments=numComments;
     this.totalActivity=totalActivity;

 }

    @Override
    public String toString() {
        return "UserActivity{" +
                "username='" + username + '\'' +
                ", numPostings=" + numPostings +
                ", numComments=" + numComments +
                ", totalActivity=" + totalActivity +
                '}';
    }
}
