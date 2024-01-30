package com.example.LargeScaleProject.test.MongoTest;

import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.UserWatchTimeResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.WatchlistSpoiler;
import com.example.LargeScaleProject.Model.MongoDB.Movie;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbWatchlistservice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MongodbWatchlistserviceTest {

    @Autowired
    private MongodbUserService userService;

    @Autowired
    private MongodbMovieService mongodbMovieService;

    @Autowired
    private MongodbWatchlistservice watchlistService;

    @BeforeAll
    public static void setup(@Autowired MongodbUserService userService) {
        //inseriamo un utente test per verificare le funzionalità
        RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
                25, "password123", new ArrayList<>(), "Test City");
        if(userService.findByUserName("testUser").isPresent()){
            userService.deleteUser(userService.findByUserName("testUser").get().get_id());
            userService.addUser(user);
        }else{
            userService.addUser(user);
        }
        //useremo un film presente nel db per testare le funzionalità della watchlist
    }
    @AfterAll
    public static void cleanup(@Autowired MongodbUserService userService) {
        //cancelliamo l'utente creato con la setup per mantenere lo stato del database
        if(userService.findByUserName("testUser").isPresent())
            userService.deleteUser(userService.findByUserName("testUser").get().get_id());
    }


    @Test
    @Order(1)
    void testAddWatchlistItemSuccess() {
        Movie movie = new Movie("football-freaks", "Football Freaks", 0);
        WatchlistItem film_to_add = new WatchlistItem(movie, 0);
        // Test, il film viene aggiunto in testa
        Optional<RegisteredUser> result = watchlistService.addWatchlistItem(userService.findByUserName("testUser").get().get_id(), film_to_add);

        // Verify
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getWatchlist().size());
        assertEquals("football-freaks", result.get().getWatchlist().get(0).getMovie().getMovie_id());
    }

    @Test
    @Order(3)
    void testRemoveWatchlistItem_UserNotFound() {
        // Setup
        String user_id = "nonexistentuser"; //user non esistente
        String movie_id = "football-freaks"; //film esistente
        // Test & Verify
        assertThrows(UserNotFoundException.class,
                () -> watchlistService.removeWatchlistItem(user_id, movie_id));

    }

    @Test
    @Order(2)
    void testRemoveWatchlistItem_ItemNotFound() {
        // Setup
        String user_id = "testUser"; //user esistente
        String movie_id = "nonexistentmovie"; //film non esistente
        RegisteredUser existingUser = new RegisteredUser();
        existingUser.setUsername(user_id);
        existingUser.setWatchlist(new ArrayList<>());
        // Act & Assert: Chiamata al metodo e verifica dell'eccezione
        UserException exception = assertThrows(UserException.class,
                () -> watchlistService.removeWatchlistItem(user_id, movie_id));

        // Then
        assertEquals("Error while removing an item from the watchlist", exception.getMessage());
    }

    @Test
    @Order(4)
    public void testWatchMovie() {
        // Setup
        String username = "testUser";
        String movie_id = "football-freaks";

        // Execute
        watchlistService.watchMovie(username, movie_id);

        // Verifica
        RegisteredUser userAfterWatch = userService.findByUserName(username).get();
        assertEquals(1, userAfterWatch.getWatchlist().get(0).getWatch()); //facendo il test sul nostro film aggiunto ora è il primo dato che l'inserimento avviene in test
        System.out.println("stato del watch correttamente modificato");
    }


    @Test
    @Order(5)
    public void testgetFirst10WatchedMovies() {
        // Setup
        String username = "madmann14";
        RegisteredUser unwatchedMovies = watchlistService.getFirst10WatchedMovies(username);
        for(WatchlistItem i : unwatchedMovies.getWatchlist()){
            System.out.println(i);
        }
    }
    @Test
    @Order(6)
    public void testgetFirst10UnwatchedMovies() {
        // Setup
        String username = "madmann14";
        RegisteredUser unwatchedMovies = watchlistService.findUnwatchedMovies(username);
                for(WatchlistItem i : unwatchedMovies.getWatchlist()){
                    System.out.println(i);
                }
    }
    @Test
    @Order(7)
    public void testcalculateUserWatchTime() {
        String username = "vinsim27";
        UserWatchTimeResult result = new UserWatchTimeResult( watchlistService.calculateUserWatchTime(username) );
        System.out.println(result);
    }
}
