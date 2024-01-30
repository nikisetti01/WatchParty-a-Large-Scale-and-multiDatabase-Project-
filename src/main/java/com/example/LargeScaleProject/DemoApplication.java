package com.example.LargeScaleProject;
import com.example.LargeScaleProject.Model.MongoDB.Admin;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbWatchlistservice;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommentService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Optional;


@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.example.LargeScaleProject.Repository.Neo4jDB")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner testUserService(MongodbUserService mongodbUserService, MongodbWatchlistservice mongodbWatchlistservice, MongodbMovieService mongodbMovieService, MongodbReviewService mongodbReviewService, Neo4jUserService neo4jUserService, Neo4jCommunityService
            neo4jCommunityService, Neo4jPostService neo4jPostService, Neo4jCommentService neo4jCommentService) {
        return args -> {


        };


        };
    }



// System.out.println();

/* TENERE NON TOGLIERE SERVIRANNO NEL CONTROLLER PORCODIOOOOOOOOO
//Conta quanti film sono stati visti (watch = 1) in ciascuna fascia d'età. Può essere utile
                // per capire quali gruppi di età sono più attivi nella visione di film.
                List<AgeGroupMovieCountResult> results = userService.countMoviesByAgeGroup();

                // Ordina la lista in base all'età
                sortByAge(results);

                // Ora la lista è ordinata per età
                for (AgeGroupMovieCountResult result : results) {
                    System.out.println(result.getAgeGroup() + ": " + result.getMovieCount());
                }
// Chiamata al sevizio per trovare quanti utenti di una citta hanno visto un film
                String city = "Modena";
                String movieId = "repulsion";
                Long userCount = userService.countUsersByCityAndMovie(city, movieId);
                System.out.println("Number of users in " + city + " who have watched the movie: " + userCount);

  //calcola il totale del runtime di film visti da un utente
                String username = "the_ruck";
                UserWatchTimeResult totalRuntime = new UserWatchTimeResult(watchlistservice.calculateUserWatchTime(username));
                System.out.println("Total Runtime for user " + username + ": " + totalRuntime.getTotalRuntime() + " minutes");


 //dato un utente in ingresso mi fornisce un istrogramma con il conto di quante volte ha dato un voto in una review per ogni review data
            String userprovato = "sonnyc";
            List<RatingCountResult> ratingHistogram = reviewService.findRatingHistogramForUser(userprovato);

            System.out.println("Rating Histogram for User: " + userprovato);
            for (RatingCountResult result : ratingHistogram) {
                System.out.println("Rating: " + result.get_id() + ", Count: " + result.getCount());
            }

 VISUALIZZA  5 REW RANDOM PER I 5 TOP FILM
            List<ReviewAggregateResult> topMovies = reviewService.findTopMoviesByReviews();
            for (ReviewAggregateResult iter : topMovies ){
                List<MovieRandomReviewsResult> randomReviews = reviewService.findRandomReviewsForMovie(iter.get_id());
                        for (MovieRandomReviewsResult result : randomReviews) {
                            System.out.println("Movie ID: " + result.getMovieId());
                            System.out.println("Random Reviews:");
                            for (Review review : result.getRandomReviews()) {
                                System.out.println("Review ID: " + review.get_id());
                                System.out.println("Rating: " + review.getRating_val());
                                System.out.println("Author: " + review.getAuthorusername());
                                System.out.println("Review: " + review.getReview());
                                System.out.println("------------------------");
                    }
                }
            }

///istrogramma review per film
            List<RatingDistributionResult> ratingDistribution = reviewService.findRatingDistributionByMovie("The Hangover Part II");

            System.out.println("Rating Distribution for Movie:");
            for (RatingDistributionResult result : ratingDistribution) {
                System.out.println("Rating: " + result.get_id() + ", Count: " + result.getCount());
            }

            ///total reviews
            List<MovieReviewCountResult> reviewCounts = reviewService.findReviewCountByMovie();
            for (MovieReviewCountResult result : reviewCounts) {
                System.out.println("Movie ID: " + result.get_id());
                System.out.println("Total Reviews: " + result.getTotalReviews());
                System.out.println("--------------------------------");
            }

///trova i film con voto medio migliore
           List<ReviewAggregateResult> topMovies = reviewService.findTopMoviesByReviews();
            for (ReviewAggregateResult result : topMovies) {
                // Qui, 'result' rappresenta ciascun oggetto ReviewAggregateResult nella lista 'topMovies'
                // Puoi accedere ai campi dell'oggetto, come 'result.getMovie_id()', 'result.getTotalReviews()', ecc.
                System.out.println("Movie Title: " + movieService.findMovieId(result.get_id()).get().getMovie_title());
                System.out.println("Total Reviews: " + result.getTotalReviews());
                System.out.println("Average Rating: " + result.getAvgRating());
                System.out.println("--------------------------------");
            }

///lista utenti che hanno recensito un determinato film
            List<MovieDetailsResult> movieDetails = reviewService.findMovieDetails("The Hangover Part II");
            for (MovieDetailsResult result : movieDetails) {
                System.out.println("Movie Title: " + result.get_id());
                for(String utenti : result.getUser()){
                    System.out.println("Users: " + utenti);
                }
                System.out.println("Average Rating: " + result.getAvgRating());
                System.out.println("--------------------------------");
            }
*/
