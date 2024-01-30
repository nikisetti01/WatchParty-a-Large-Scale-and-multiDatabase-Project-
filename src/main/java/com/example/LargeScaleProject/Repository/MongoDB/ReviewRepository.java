package com.example.LargeScaleProject.Repository.MongoDB;

import com.example.LargeScaleProject.Model.MongoDB.*;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository  extends MongoRepository<Review,String> {
    // find reviews by authorusername and movie id

    @Query("{'authorusername': ?0}")
    List <Review> findReviewByAuthor(String username);
    @Query("{'movie_id': ?0, 'authorusername': ?1}")
    List<Review> findReviewByMovieIdAndAuthor(String movieId, String username);

    void deleteBy_id(String _id);
    @Query("{ 'movie.movie_id' : ?0 }")
    List <Review> findReviewByMovie_Movie_id(String movie_id);
    Optional <Review> findBy_id(String id);


    //Find the Top 5 film by avg rating_val
    @Aggregation(pipeline = {
            "{ $group: { _id: '$movie.movie_id', totalReviews: { $sum: 1 }, avgRating: { $avg: '$rating_val' } } }",
            "{ $sort: { totalReviews: -1, avgRating: -1 } }",
            "{ $limit: 5 }"
    })
    List<ReviewAggregateResult> findTopMoviesByReviews();

    ///number of user for a movie reviewed
    @Aggregation(pipeline = {
            "{ $match: { 'movie.movie_title': ?0 } }",
            "{ $group: { _id: '$movie.movie_title', user: { $push: '$authorusername' }, avgRating: { $avg: '$rating_val' } } }"
    })
    List<MovieDetailsResult> findMovieDetails(String movie_title);


    @Aggregation(pipeline = {
            "{ $group: { _id: '$movie.movie_title', totalReviews: { $sum: 1 } } }"
    })
    List<MovieReviewCountResult> findReviewCountByMovie();

    ///array of vote for a movie
    @Aggregation(pipeline = {
            "{ $match: { 'movie.movie_title': ?0 } }",
            "{ $group: { _id: '$rating_val', count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }"
    })
    List<RatingDistributionResult> findRatingDistributionByMovie(String movie_title);


    ///find top 5 reviews for a movie
    @Aggregation(pipeline = {
            "{ $match: { 'movie.movie_id': ?0 } }",
            "{ $sample: { size: 5 } }",
            "{ $group: { _id: '$movie.movie_title', randomReviews: { $push: '$$ROOT' } } }",
            "{ $project: { _id: 1, randomReviews: 1 } }"
    })
    List<MovieRandomReviewsResult> findRandomReviewsForMovie(String movie_id);

    //find the number of votes for each mark by autorusername
    @Aggregation(pipeline = {
            "{ $match: { 'authorusername': ?0 } }",
            "{ $group: { _id: '$rating_val', count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }"
    })
    List<RatingCountResult> findRatingHistogramForUser(String username);

}

