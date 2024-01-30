package com.example.LargeScaleProject.Model.MongoDB;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Setter
@Getter
@Document(collection = "review")
public class Review {
    @Setter
    @Getter
    @Id
    private String _id;
    @Setter
    @Getter
    private Movie movie;
    @Setter
    @Getter
    private Integer rating_val;
    @Setter
    @Getter
    private String authorusername;
    @Setter
    @Getter
    private String review;


    public Review(){}
    // Costruttore
    public Review(Integer rating_val, String review, Movie movie, String authorusername ) {
        this._id = UUID.randomUUID().toString();
        this.rating_val= rating_val;
        this.movie= movie;
        this.authorusername=authorusername;
        this.review = review;

    }
    public  Review(Integer rating_val){
        this.rating_val=rating_val;
    }
    @Override
    public String toString() {
        return "Review{" +
                "_id=" + _id +
                "movie=" + (movie != null ? movie.getMovie_id() : null) +
                ", rating_val=" + rating_val +
                ", user=" + authorusername+
                ", review='" + review + '\'' +
                '}';
    }
}
