package com.example.LargeScaleProject.Model.MongoDB;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "user")
public class Admin extends User {
    private String adminpassword;

    public Admin( String display_name, String username,
                           String city,String adminpassword) {
        this.set_id(UUID.randomUUID().toString());

        this.setDisplay_name(display_name);
        this.setUsername(username);


        this.setCity(city);
        this.adminpassword=adminpassword;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "_id='" + get_id() + '\'' +
                ", displayName='" + getUsername() + '\'' +
                ", userName='" + getDisplay_name() + '\'' +

                ", password='" + getAdminpassword() + '\'' +
                ", city='" + getCity() + '\'' +
                '}';
    }
}
