package com.example.LargeScaleProject.Model.MongoDB;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
@Document(collection = "user")
@Setter
@Getter
public class RegisteredUser extends User {
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private Integer age;


    private String [] tags;
   private  ArrayList<WatchlistItem> watchlist;
   public RegisteredUser(){

   }
   public RegisteredUser(String username){
       this.setUsername(username);
   }
   public RegisteredUser(String id, ArrayList<WatchlistItem> watchlist){
       this.set_id(id);
       this.watchlist=watchlist;
   }

    // Costruttore

    public RegisteredUser(String display_name, String username, String [] tags,
                          Integer age, String password, ArrayList<WatchlistItem> watchlist, String city) {
        this.set_id( UUID.randomUUID().toString());
        this.setDisplay_name(display_name);
        this.setUsername(username);
        this.tags = tags;
        this.setAge(age);
        this.setPassword(password);
        this.watchlist = watchlist;
        this.setCity(city);
    }
    public RegisteredUser(String _id,String display_name, String username, String [] tags,
                          Integer age, String password, ArrayList<WatchlistItem> watchlist, String city) {

        this.set_id(_id);
        this.setDisplay_name(display_name);
        this.setUsername(username);
        this.tags = tags;
        this.setAge(age);
        this.setPassword(password);
        this.watchlist = watchlist;
        this.setCity(city);
    }
    public RegisteredUser(String display_name, String username,
                          Integer age, String password, String city) {
        this.set_id( UUID.randomUUID().toString());
        this.setDisplay_name(display_name);
        this.setUsername(username);

        this.setAge(age);
        this.setPassword(password);

        this.setCity(city);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RegisteredUser{");
        sb.append("_id='").append(get_id()).append('\'').append('\n');
        if(this.getDisplay_name()  != null)
            sb.append(", displayName='").append(getDisplay_name()).append('\'').append('\n');
        if(this.getUsername()  != null)
           sb.append(", userName='").append(getUsername()).append('\'').append('\n');

        // Verifica se i campi sono nulli prima di stamparli
        if (tags != null) {
            sb.append(", tags=").append(Arrays.toString(tags)).append('\n');
        }

        sb.append(", age=").append(getAge()).append('\n');
        sb.append(", password='").append(getPassword()).append('\'').append('\n');

        // Verifica se il campo watchlist è null prima di stamparlo
        if (watchlist != null) {
            sb.append(", watchlist=").append(watchlist.toString()).append('\n');
        }

        sb.append(", city='").append(getCity()).append('\'').append('\n');
        sb.append('}');
        return sb.toString();
    }


}
