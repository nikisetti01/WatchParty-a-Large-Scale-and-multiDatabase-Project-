package com.example.LargeScaleProject.Controller;

import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.httpRequest.Topfilmrequest;
import com.example.LargeScaleProject.Service.Dataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final Dataservice movieService;

    @Autowired
    public MovieController(Dataservice movieService) {
        this.movieService =movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Optional<Movie> movie = movieService.findmoviebyId(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/find/title/{title}")
    public ResponseEntity<List<Movie>> findMovieByTitle(@PathVariable String title) {
        System.out.println("Search for movie with title: " + title);
        List<Optional<Movie>> movieList =movieService.findmoviebytitle(title);

        // Estrai i Movie dagli Optional e rimuovi quelli vuoti
        List<Movie> movies = movieList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Verifica se la lista è vuota
        if (!movies.isEmpty()) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/top5films")
    public ResponseEntity<List<Topfilmrequest>> getTopFilms() {
        try {
            List<Topfilmrequest> topFilms = movieService.getTopFilm();
            return new ResponseEntity<>(topFilms, HttpStatus.OK);
        } catch (Exception e) {
            // Log dell'eccezione e restituire un errore generico
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Aggiungi altri metodi del controller se necessario per soddisfare le tue esigenze
}
