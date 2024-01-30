package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class joinRequest {
    private String username;
    private String communityname;

    public joinRequest(String username, String communityname) {
        this.username = username;
        this.communityname = communityname;
    }
}
