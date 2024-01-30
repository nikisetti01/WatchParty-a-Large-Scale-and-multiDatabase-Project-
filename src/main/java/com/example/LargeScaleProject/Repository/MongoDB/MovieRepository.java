package com.example.LargeScaleProject.Repository.MongoDB;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository <Movie,String> {
    // find movie by title, id and movie_id

    void deleteMovieBy_id(String _id);
    Optional<Movie>  findMovieBy_id(String id);
    @Query("{ 'movie_title' : ?0 }")
    List <Optional<Movie>>  findMovieByMovie_title(String title);
   // Optional<Movie>findMovieByMovieTitle(String movie_title);
    @Query("{'movie_id':  ?0}")
    Optional<Movie> findMovieByMovie_id(String movie_id);

}
