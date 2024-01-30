package com.example.LargeScaleProject.Service.MongoDB;
import com.example.LargeScaleProject.Exception.MovieNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Exception.MovieException;
import com.example.LargeScaleProject.Repository.MongoDB.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongodbMovieService {
    @Autowired
    private MovieRepository movie_repository;
    public Optional<Movie> addMovie(Movie movie){
        try {
            // Controlla se il film con lo stesso _id è già presente
            Optional<Movie> existingMovie = movie_repository.findMovieByMovie_id(movie.getMovie_id());

            if (existingMovie.isPresent()) {
                // Eccezione se il film con lo stesso _id è già presente
                throw new DuplicateKeyException("Movie with the same ID already exists");
            }

            // Salva il nuovo film
            Movie savedMovie = movie_repository.save(movie);
            return Optional.of(savedMovie);

        } catch (Exception e) {
            throw new MovieException("Error while adding a movie", e);
        }
    }

    //delete per ID
    public String DeleteMovie(String _id){
        try {
            Optional<Movie> deletedMovie = movie_repository.findMovieBy_id(_id);

            if (deletedMovie.isPresent()) {
                String movie_id = deletedMovie.get().getMovie_id();
                movie_repository.deleteMovieBy_id(_id);
                return "Movie " + movie_id + " rimosso";
            } else {
                throw new MovieNotFoundException("Movie non esiste");
            }
        } catch (MovieNotFoundException e) {
            throw e; // Rilancia direttamente l'eccezione MovieNotFoundException
        } catch (Exception e) {
            throw new MovieException("Error while deleting a movie", e);
        }
    }

   //find
    public List <Optional<Movie>> findBytitle(String movie_title){
        try{
           List<Optional<Movie>> MovieFound = movie_repository.findMovieByMovie_title(movie_title);
            if(!MovieFound.isEmpty()){
                return MovieFound;
            }else {
                throw new MovieNotFoundException("Movie non esiste");
            }
        } catch (Exception e) {
            throw new MovieException("Error while finding a movie", e);
        }
    }

    //trova film tramite movie_id (non _id)
    public Optional<Movie> findMovieId(String movie_id){
        try{
            Optional<Movie> MovieFound = movie_repository.findMovieByMovie_id(movie_id);
            if(MovieFound.isPresent()){
                return MovieFound;
            }else {
                throw new MovieNotFoundException("Movie non esiste");
            }
        } catch (Exception e) {
            throw new MovieException("Error while finding a movie", e);
        }
    }






}
