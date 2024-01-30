package com.example.LargeScaleProject.Model.MongoDB;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document (collection = "user")
public abstract class User {
    //
    @Setter
    @Getter
    @Id
    private String _id;
    @Setter
    @Getter
    private String display_name;
    @Setter
    @Getter
    private String username;


    @Setter
    @Getter
    private String city;

}
