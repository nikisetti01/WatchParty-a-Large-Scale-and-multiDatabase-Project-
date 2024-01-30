package com.example.LargeScaleProject.Service.MongoDB;

import com.example.LargeScaleProject.Exception.MovieNotFoundException;
import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.UserWatchTimeResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.WatchlistSpoiler;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import com.example.LargeScaleProject.Repository.MongoDB.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongodbWatchlistservice {

    @Autowired
    private RegisteredUserRepository userRepository;
    @Autowired
    private MongodbMovieService mongodbMovieService;


    public Optional<RegisteredUser> addWatchlistItem(String user_id, WatchlistItem watchlistItem) {
        try {


            String movie_id = watchlistItem.getMovie().getMovie_id();
            Optional<RegisteredUser> existingUser = userRepository.findByUsername(user_id);
            Optional<Movie> existingMovie = mongodbMovieService.findMovieId(movie_id);
            if (existingUser.isPresent() && existingMovie.isPresent()) {
                watchlistItem.getMovie().setRuntime(existingMovie.get().getRuntime());
                watchlistItem.getMovie().setMovie_title(existingMovie.get().getMovie_title());
                RegisteredUser user = existingUser.get();
                if (user.getWatchlist() == null) {
                    ArrayList<WatchlistItem> newatch = new ArrayList<>();
                    newatch.add(watchlistItem);
                    return Optional.of(userRepository.save(new RegisteredUser(user.get_id(), user.getDisplay_name(), user.getUsername(), user.getTags(), user.getAge(), user.getPassword(), newatch, user.getCity())));


                } else {
                    ArrayList<WatchlistItem> watchlist = user.getWatchlist();
                    Optional<WatchlistItem> movieInWatchlist = watchlist.stream()
                            .filter(item -> movie_id.equals(item.getMovie().getMovie_id()))
                            .findFirst();
                    if (movieInWatchlist.isPresent() && (movieInWatchlist.get().getWatch() == 0) && (watchlistItem.getWatch() == 1)) {
                        int index = watchlist.indexOf(movieInWatchlist.get());
                        watchlist.set(index, watchlistItem);
                        user.setWatchlist(watchlist);
                        userRepository.save(user);
                        return Optional.of(user);


                    } else if (movieInWatchlist.isEmpty()) {
                        user.getWatchlist().add(0, watchlistItem);
                        userRepository.save(user);
                        return Optional.of(user);
                    } else {
                        return Optional.empty();
                    }
                }
            } else {
                throw new UserNotFoundException("User or Movie does not exist");
            }
        } catch (Exception e) {
            throw new UserException("Error while adding an item to the watchlist", e);
        }
    }


    //funzione che rimuove un film dalla watchlist
    public Optional<RegisteredUser> removeWatchlistItem(String userId, String movieId) {
        try {
            Optional<RegisteredUser> existingUser = userRepository.findByUsername(userId);

            if (existingUser.isPresent()) {
                RegisteredUser user = existingUser.get();
                // Cerca l'elemento nella watchlist con il movieId specificato
                WatchlistItem itemToRemove = user.getWatchlist().stream()
                        .filter(item -> item.getMovie().getMovie_id().equals(movieId))
                        .findFirst()
                        .orElseThrow(() -> new MovieNotFoundException("Movie not found in the watchlist"));
                userRepository.save(user);
                return Optional.of(user);
            } else {
                throw new UserNotFoundException("User does not exist");
            }
        }catch (UserNotFoundException unf) {
            System.out.println("Caught UserNotFoundException: " + unf.getMessage());
            throw unf;
        } catch (Exception e) {
            throw new UserException("Error while removing an item from the watchlist", e);
        }
    }

    // questa funzione modifica lo stato di un film nella watchlist impostando (se esiste) da 0 a 1 nel campo watch
    // Trova l'utente per username
    public void watchMovie(String username, String movieId) {
        Optional<RegisteredUser> userop = userRepository.findByUsername(username);

        if (userop.isPresent()) {
            RegisteredUser user = userop.get();
            // Ottieni la watchlist dell'utente

            ArrayList<WatchlistItem> watchlist = user.getWatchlist();

            // Cerca l'elemento nella watchlist con il movieId specificato
            Optional<WatchlistItem> movieInWatchlist = watchlist.stream()
                    .filter(item -> movieId.equals(item.getMovie().getMovie_id()))
                    .findFirst();
            // Se l'elemento è presente, imposta l'attributo "watch" a 1
            movieInWatchlist.ifPresent(item -> item.setWatch(1));
            // Aggiorna la watchlist nell'utente
            user.setWatchlist(watchlist);

            // Salva l'utente aggiornato
            userRepository.save(user);
        } else {
            // L'utente non è stato trovato
            throw new UserNotFoundException("Utente non trovato con username: " + username);
        }
    }

    //restituisce i primi 10 film  visti
    public RegisteredUser getFirst10WatchedMovies(String username) {
        return userRepository.findWatchlistwatched(username);
    }

    //restituisce i primi 10 film non visti
    public  RegisteredUser findUnwatchedMovies(String username) {
        return userRepository.findWatchlisttowatch(username);
    }


    //calcola il totale del runtime di film visti da un utente
    public Integer calculateUserWatchTime(String username) {
        Optional<UserWatchTimeResult> result = userRepository.calculateUserWatchTime(username);
        return result.map(UserWatchTimeResult::getTotalRuntime).orElse(0);
    }


}
