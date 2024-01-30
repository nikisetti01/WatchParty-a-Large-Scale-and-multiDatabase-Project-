package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
  private   String username;
  private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
