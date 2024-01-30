package com.example.LargeScaleProject.Controller;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import com.example.LargeScaleProject.Model.httpRequest.InfoRequest;
import com.example.LargeScaleProject.Model.httpRequest.LoginRequest;
import com.example.LargeScaleProject.Model.httpRequest.ReviewRequest;
import com.example.LargeScaleProject.Model.httpRequest.WatchlistAddRequest;
import com.example.LargeScaleProject.Service.*;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final Dataservice reviewService;
    @Autowired
    public ReviewController(Dataservice reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/findbyfilm/{movie_id}")
    public ResponseEntity<List<ReviewRequest>> getReviewbyFilm(@PathVariable String movie_id) {
        // Logica per ottenere gli elementi della Watchlist per l'utente
        return ResponseEntity.ok(reviewService.ReviewbyFilm(movie_id));
    }
    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ReviewRequest reviewRequest){
try{   System.out.println(reviewRequest);
        reviewService.AddReview(reviewRequest);

    return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }catch (Exception e) {
    return new ResponseEntity<>("Error adding review: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

}
    }


}
