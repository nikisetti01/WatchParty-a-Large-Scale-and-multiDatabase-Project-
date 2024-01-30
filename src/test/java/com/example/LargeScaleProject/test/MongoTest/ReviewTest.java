package com.example.LargeScaleProject.test.MongoTest;

import com.example.LargeScaleProject.Model.MongoDB.Aggregate.*;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Repository.MongoDB.ReviewRepository;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.helpers.MethodDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ReviewTest {

    @Autowired
    private MongodbReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongodbUserService userService;
    @Autowired
    private MongodbMovieService movieService;


    // Test aggiunta recensione
    @Test
    void TestaddReview() {
        // Prepara i dati di test
        Movie movie = movieService.findMovieId("football-freaks").get();
        Review review = new Review(5, "Great movie!", movie, "testUser");

        // Esegui la funzione di aggiunta recensione
        Optional<Review> savedReview = reviewService.addReview(review);
        // Verifica che la recensione sia stata salvata correttamente
        assertTrue(savedReview.isPresent());
        assertEquals(review.getRating_val(), savedReview.get().getRating_val());
        assertEquals(review.getReview(), savedReview.get().getReview());
    }

    // Test eliminazione recensione
    @Test
    void TestdeleteReview() {
        // Prepara i dati di test
        Movie movie = movieService.findMovieId("football-freaks").get();
        Review review = new Review(5, "Great movie!", movie, "testUser");
        Review savedReview = reviewRepository.save(review);

        // Esegui la funzione di eliminazione recensione
        String result = reviewService.deleteReview(savedReview.get_id());

        // Verifica che la recensione sia stata eliminata correttamente
        assertEquals("Review " + savedReview.get_id() + " removed", result);
    }

    // Test ricerca recensioni per username
    @Test
    void TestFindUserReviews() {

        // Esegui la funzione di ricerca recensioni per username
        List<Review> reviews = reviewService.FindReviewsAuthor("testUser");
        // Verifica che la lista non sia vuota
        for(Review iter : reviews){
            System.out.println(iter);
        }
        assertTrue(!reviews.isEmpty());
    }

    //test funzioni aggregate
    @Test
    void TestFindTopMoviesByReviews() {
        List<ReviewAggregateResult> topMovies = reviewService.findTopMoviesByReviews();
        for (ReviewAggregateResult result : topMovies) {
            // Qui, 'result' rappresenta ciascun oggetto ReviewAggregateResult nella lista 'topMovies'
            // Puoi accedere ai campi dell'oggetto, come 'result.getMovie_id()', 'result.getTotalReviews()', ecc.
            System.out.println("Movie Title: " + movieService.findMovieId(result.get_id()).get().getMovie_title());
            System.out.println("Total Reviews: " + result.getTotalReviews());
            System.out.println("Average Rating: " + result.getAvgRating());
            System.out.println("--------------------------------");
        }
    }


    @Test
    void TestfindMovieDetails() {
        List<MovieDetailsResult> movieDetails = reviewService.findMovieDetails("The Hangover Part II");
        System.out.println(movieDetails);
        for (MovieDetailsResult result : movieDetails) {
            System.out.println("Movie Title: " + result.get_id());
            for (String utenti : result.getUser()) {
                System.out.println("Users: " + utenti);
            }
            System.out.println("Average Rating: " + result.getAvgRating());
            System.out.println("--------------------------------");
        }
    }

    @Test
    void TestFindRandomReviewsForMovie() {
        List<MovieRandomReviewsResult> randomReviewsList = reviewService.findRandomReviewsForMovie("The Hangover Part II");

        for (MovieRandomReviewsResult result : randomReviewsList) {
            System.out.println("Movie ID: " + result.getMovieId());

            List<Review> randomReviews = result.getRandomReviews();
            for (int i = 0; i < randomReviews.size(); i++) {
                Review randomReview = randomReviews.get(i);
                System.out.println("Review " + (i + 1) + ":");
                System.out.println("  Review ID: " + randomReview.get_id());
                System.out.println("  Rating: " + randomReview.getRating_val());
                System.out.println("  Author: " + randomReview.getAuthorusername());
                System.out.println("  Review: " + randomReview.getReview());
                System.out.println("-------------------------");
            }

            System.out.println();
        }
    }

    @Test
    void TesttFindRatingHistogramForUser() {
        String userprovato = "sonnyc";
        List<RatingCountResult> ratingHistogram = reviewService.findRatingHistogramForUser(userprovato);

        System.out.println("Rating Histogram for User: " + userprovato);
        for (RatingCountResult result : ratingHistogram) {
            System.out.println("Rating: " + result.get_id() + ", Count: " + result.getCount());
        }
    }

    @BeforeAll
    public static void setup(@Autowired MongodbUserService userService) {
        RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
                25, "password123", new ArrayList<>(), "Test City");
        userService.addUser(user);
    }

}