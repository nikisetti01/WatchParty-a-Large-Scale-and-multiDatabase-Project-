package com.example.LargeScaleProject.Model.httpRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoRequest {
  private   String display_name;
  private   String password;
  private   Integer age;
  private   String city;
  private String[] tags;

    public InfoRequest(String display_name, String password, Integer age, String city,String [] tags) {
        this.display_name = display_name;
        this.password = password;
        this.age = age;
        this.city = city;
        this.tags=tags;
    }
}
