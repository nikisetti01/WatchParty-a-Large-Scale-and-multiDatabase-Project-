package com.example.LargeScaleProject.ConsistencyCheck;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.Review;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbWatchlistservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistConsistencyChecker {

    private final MongodbUserService mongodbUserService;
    private final MongodbReviewService mongodbReviewService;


    @Autowired
    public WatchlistConsistencyChecker(MongodbUserService mongodbUserService, MongodbReviewService mongodbReviewService) {
        this.mongodbUserService = mongodbUserService;
        this.mongodbReviewService = mongodbReviewService;

    }

    public boolean synchronizeWatchlistWithReviews(String username) {
        // Ottieni l'utente registrato e le sue recensioni
        Optional<RegisteredUser> userOptional = mongodbUserService.findByUserName(username);
        if (!userOptional.isPresent()) {
            return false; // Utente non trovato
        }
        RegisteredUser user = userOptional.get();
        List<Review> reviews = mongodbReviewService.FindReviewsAuthor(username);

        // Converti le recensioni in una mappa per un accesso rapido
        Map<String, Review> reviewsMap = reviews.stream()
                .collect(Collectors.toMap(
                        review -> review.getMovie().getMovie_id(),
                        review -> review
                ));

        // Ottieni la watchlist dell'utente
        ArrayList<WatchlistItem> watchlist = new ArrayList<>(user.getWatchlist());
        boolean watchlistModified = false;

        // Sincronizza ogni elemento della watchlist con le recensioni
        List<WatchlistItem> itemsToRemove = new ArrayList<>();
        for (WatchlistItem item : watchlist) {
            String movieId = item.getMovie().getMovie_id();
            Review correspondingReview = reviewsMap.get(movieId);

            if (item.getWatch() == 0 && correspondingReview != null) {
                item.setWatch(1);
                item.setReview(new Review(correspondingReview.getRating_val()));
                watchlistModified = true;
            }
            else if (item.getWatch() == 1 && correspondingReview != null &&
                    !item.getReview().getRating_val().equals(correspondingReview.getRating_val())) {
                item.setReview(new Review(correspondingReview.getRating_val()));
                watchlistModified = true;
            }
            else if (correspondingReview == null && item.getWatch() == 1) {
                itemsToRemove.add(item);
                watchlistModified = true;
            }
        }

        watchlist.removeAll(itemsToRemove);

        // Caso 3: Aggiungi film mancanti dalla watchlist
        for (Review review : reviews) {
            if (!watchlist.stream().anyMatch(item -> item.getMovie().getMovie_id().equals(review.getMovie().getMovie_id()))) {
                WatchlistItem newItem = new WatchlistItem(review.getMovie(), new Review(review.getRating_val()), 1);
                watchlist.add(newItem);
                watchlistModified = true;
            }
        }


        if (watchlistModified) {
            user.setWatchlist(watchlist);
            mongodbUserService.update(user);
        }
        return watchlistModified;
    }
}
