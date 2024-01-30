package com.example.LargeScaleProject.test.MongoTest;
import com.example.LargeScaleProject.Exception.MovieException;
import com.example.LargeScaleProject.Exception.MovieNotFoundException;
import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Repository.MongoDB.MovieRepository;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.swing.text.StyleConstants.Size;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MongodbMovieService movieService;


    @Test
    @Order(1)
    public void testAddMovieSuccess() {
        // Arrange
        String[] genres = {"Horror", "Drama"};
        String movieId = "test-movie";
        String movieTitle = "Test Movie";
        String originalLanguage = "English";
        String overview = "This is a test movie overview.";
        String[] productionCountries = {"USA", "Canada"};
        String releaseDate = "2022-01-01";
        Integer runtime = 120;
        String type = "Movie";
        Movie testMovie = new Movie(genres, movieId, movieTitle, originalLanguage,
                overview, productionCountries, releaseDate, runtime, type);
        // Act
        Optional<Movie> result = movieService.addMovie(testMovie);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(testMovie, result.get());
    }

    @Test
    @Order(2)
    public void testAddMovieDuplicateKeyException() {
        //proviamo a ricreare il film sopra così da verificare che non possano essere inseriti duplicati
        String[] genres = {"Horror", "Drama"};
        String movieId = "test-movie";
        String movieTitle = "Test Movie";
        String originalLanguage = "English";
        String overview = "This is a test movie overview.";
        String[] productionCountries = {"USA", "Canada"};
        String releaseDate = "2022-01-01";
        Integer runtime = 120;
        String type = "Movie";
        Movie testMovie = new Movie(genres, movieId, movieTitle, originalLanguage,
                overview, productionCountries, releaseDate, runtime, type);

        // Act and Assert
        assertThrows(Exception.class, () -> movieService.addMovie(testMovie));
    }

    @Test
    @Order(4)
    public void testDeleteMovieSuccess() {
        // Arrange
        String movie_id = "test-movie";

        //infine lo cancelliamo per testare la funzione delete
        String result = movieService.DeleteMovie(movieService.findMovieId("test-movie").get().get_id());

        // Assert
        assertEquals( "Movie " + movie_id + " rimosso", result);
        assertFalse(movieRepository.existsById(movie_id));
    }

    @Test
    @Order(3)
    public void testDeleteMovieNotFound() {
        // Arrange
        String nonExistingMovieId = "nonExistingMovieId";


        // Test and verify
        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> {
            movieService.DeleteMovie(nonExistingMovieId);
        });

        // Assert
        assertEquals("Movie non esiste", exception.getMessage());
    }

    @Test
    public void testFindByTitle() {
        // Setup, useremo un film presente in più versioni nel database, distinte per anno e titoli e altri attributi diversi (es: il runtime)
        String existingMovieTitle = "Aftermath";

        // Test
        List<Optional<Movie>> actualMovies = movieService.findBytitle(existingMovieTitle);
        for (Optional<Movie> movieOptional : actualMovies) {
            if (movieOptional.isPresent()) {
                Movie movie = movieOptional.get();
                System.out.println("Movie ID: " + movie.getMovie_id() +
                        ", Title: " + movie.getMovie_title() +
                        ", Runtime: " + movie.getRuntime());
            } else {
                System.out.println("Optional<Movie> is empty");
            }
        }
        // Assert
        assertEquals(13, actualMovies.size());
    }


}
