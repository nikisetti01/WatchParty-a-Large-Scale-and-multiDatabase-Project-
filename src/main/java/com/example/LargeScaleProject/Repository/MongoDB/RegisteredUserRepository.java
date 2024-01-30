package com.example.LargeScaleProject.Repository.MongoDB;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.AgeGroupMovieCountResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.MostWatchedMovieResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.UserWatchTimeResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.WatchlistSpoiler;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RegisteredUserRepository extends MongoRepository <RegisteredUser,String> {
    void deleteBy_id(String _id);
    Optional<RegisteredUser> findBy_id(String _id);
    @Query("{ 'username' : ?0 }")
    Optional <RegisteredUser> findByUsername(String username);

    //find all the watchlist of a user element  watched
    @Aggregation(pipeline = {
            "{ $match: { 'username': ?0 } }",
            "{ $project: { 'username': 1, 'watchlist': { $filter: { input: '$watchlist', as: 'item', cond: { $eq: ['$$item.watch', 1] } } } } }"
    })
    Optional<RegisteredUser> ExpandWatchlistWatched (String username);
    // find watchlist by user element to watch
    @Aggregation(pipeline = {
            "{ $match: { 'username': ?0 } }",
            "{ $project: { 'username': 1, 'watchlist': { $filter: { input: '$watchlist', as: 'item', cond: { $eq: ['$$item.watch', 0] } } } } }"
    })
    Optional<RegisteredUser> ExpandWatchlistUnwatched (String username);

    @Aggregation(pipeline = {

            "{ $match: { 'username': ?0 } }",

            "{ $project: { 'username': 1, 'watchlist': { $filter: { input: '$watchlist', as: 'item', cond: { $eq: ['$$item.watch', 1] } } } } }",

            "{ $project: { 'username': 1, 'watchlist': { $slice: ['$watchlist', 10] } } }"

    })
    RegisteredUser findWatchlistwatched(String username);
    @Aggregation(pipeline = {

            "{ $match: { 'username': ?0 } }",

            "{ $project: { 'username': 1, 'watchlist': { $filter: { input: '$watchlist', as: 'item', cond: { $eq: ['$$item.watch', 0] } } } } }",

            "{ $project: { 'username': 1, 'watchlist': { $slice: ['$watchlist', 10] } } }"

    })
    RegisteredUser findWatchlisttowatch(String username);
// Calculate the total runtime of the movie watched by a user
    @Aggregation(pipeline = {
            "{ $match: { 'username': ?0 } }",
            "{ $project: { " +
                    "    totalRuntime: { " +
                    "        $reduce: { " +
                    "            input: { " +
                    "                $filter: { " +
                    "                    input: '$watchlist', " +
                    "                    as: 'item', " +
                    "                    cond: { $eq: ['$$item.watch', 1] } " +
                    "                } " +
                    "            }, " +
                    "            initialValue: 0, " +
                    "            in: { $add: ['$$value', '$$this.movie.runtime'] } " +
                    "        } " +
                    "    } " +
                    "}} "
    })

    Optional<UserWatchTimeResult> calculateUserWatchTime(String username);



    // Number of user by city
    @Aggregation(pipeline = {
            "{ $match: { 'city': ?0, 'watchlist.movie.movie_id': ?1, 'watchlist.watch': 1 } }",
            "{ $count: 'userCount' }"
    })
    Optional<MostWatchedMovieResult> countUsersByCityAndMovie(String city, String movieId);


    //Calculate an array that find how many movie are watched for age range
    @Aggregation(pipeline = {
            "{ $match: { watchlist: { $ne: null } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 10] }, { $lt: ['$age', 20] } ] }, then: '10-19', else: null } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 20] }, { $lt: ['$age', 30] } ] }, then: '20-29', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 30] }, { $lt: ['$age', 40] } ] }, then: '30-39', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 40] }, { $lt: ['$age', 50] } ] }, then: '40-49', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 50] }, { $lt: ['$age', 60] } ] }, then: '50-59', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 60] }, { $lt: ['$age', 70] } ] }, then: '60-69', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $and: [ { $gte: ['$age', 70] }, { $lt: ['$age', 80] } ] }, then: '70-79', else: '$ageGroup' } } } }",
            "{ $addFields: { ageGroup: { $cond: { if: { $gte: ['$age', 80] }, then: '80+', else: '$ageGroup' } } } }",
            "{ $group: { _id: '$ageGroup', movieCount: { $sum: { $size: { $filter: { input: '$watchlist', cond: { $eq: ['$$this.watch', 1] } } } } } } }",
            "{ $project: { _id: 0, ageGroup: '$_id', movieCount: 1 } }"
    })
    List<AgeGroupMovieCountResult> countMoviesByAgeGroup();

}
