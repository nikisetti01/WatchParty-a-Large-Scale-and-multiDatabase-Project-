package com.example.LargeScaleProject.Service.MongoDB;

import com.example.LargeScaleProject.Exception.ReviewException;
import com.example.LargeScaleProject.Model.MongoDB.*;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.*;
import com.example.LargeScaleProject.Repository.MongoDB.RegisteredUserRepository;
import com.example.LargeScaleProject.Repository.MongoDB.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MongodbReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongodbMovieService mongodbMovieService;
    @Autowired
    private MongodbUserService mongodbUserService;
    @Autowired
    private RegisteredUserRepository userRepository;

    public Optional<Review> addReview(Review review) {
        try {
            Optional<Movie> movie = mongodbMovieService.findMovieId(review.getMovie().getMovie_id());
            Optional<RegisteredUser> user = mongodbUserService.findByUserName(review.getAuthorusername());
            if (movie.isEmpty())
                throw new ReviewException("Movie is not found");
            else if (user.isEmpty())
                throw new ReviewException("USER is not found");
            else {
              List <Review> list=  reviewRepository.findReviewByAuthor(review.getAuthorusername());
              for (Review elem: list){
                  if(Objects.equals(elem.getMovie().getMovie_id(), review.getMovie().getMovie_id()))
                      return Optional.empty();
              }


                Review savedReview = reviewRepository.save(review);
                review.setMovie(movie.get());
                review.setAuthorusername(review.getAuthorusername());
                return Optional.of(savedReview);
            }
        } catch (Exception e) {
            throw new ReviewException("Error while adding a review", e);
        }

    }

    public String deleteReview(String _id) {
        try {
            Optional<Review> deletedReview = reviewRepository.findBy_id(_id);
            if (deletedReview.isPresent()) {
                reviewRepository.deleteBy_id(_id);
                return "Review " + _id + " removed";
            } else {
                return "Review " + _id + " does not exist";
            }
        } catch (Exception e) {
            throw new ReviewException("Error while deleting a review", e);
        }
    }

    public List<Review> FindReviewsAuthor(String username) {
        try {
            Optional <RegisteredUser> user = mongodbUserService.findByUserName(username);
            String author=user.get().getUsername();
            List <Review> reviewsFound = reviewRepository.findReviewByAuthor(author);
            if (!reviewsFound.isEmpty()) {
                return reviewsFound;
            } else {
                // Se non viene trovata nessuna recensione per l'utente, restituisci un Optional vuoto.
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new ReviewException("Error while finding reviews by user username", e);
        }
    }

    public List<Review> findByMovieId(String movieId) {
        try {
            List <Review> reviewsFound = reviewRepository.findReviewByMovie_Movie_id(movieId);
            if (!reviewsFound.isEmpty()) {
                return reviewsFound;
            } else {
                // Se non vengono trovate recensioni per il movie_id, restituisci un Optional vuoto.
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new ReviewException("Errore durante la ricerca delle recensioni per movie_id", e);
        }
    }
    public Review findByMovieIdandusername(String movie_id, String username){
        return reviewRepository.findReviewByMovieIdAndAuthor(movie_id,username).get(0);
    }


    //aggregate dei 5 film con media migliore
    public List<ReviewAggregateResult> findTopMoviesByReviews() {
        return reviewRepository.findTopMoviesByReviews();
    }

    //aggregate lista utenti che hanno recensito un film
    public List<MovieDetailsResult> findMovieDetails(String movie_title) {
        return reviewRepository.findMovieDetails(movie_title);
    }

    //aggregate conteggio recensioni di un film
    public List<MovieReviewCountResult> findReviewCountByMovie() {
        return reviewRepository.findReviewCountByMovie();
    }

    //istrogramma recensioni per film specifico
    public List<RatingDistributionResult> findRatingDistributionByMovie(String movie_title) {
        return reviewRepository.findRatingDistributionByMovie(movie_title);
    }

    ///vedi i 5 film con valore più alto di reviews e restituisce 5 reviews random
    public List<MovieRandomReviewsResult> findRandomReviewsForMovie(String movie_title) {
        return reviewRepository.findRandomReviewsForMovie(movie_title);
    }

    //dato un utente in ingresso mi fornisce un istrogramma con il conto di quante volte ha dato un voto in una review per ogni review data
    public List<RatingCountResult> findRatingHistogramForUser(String username) {
        return reviewRepository.findRatingHistogramForUser(username);
    }


}
