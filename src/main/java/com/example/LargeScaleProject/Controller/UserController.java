package com.example.LargeScaleProject.Controller;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import com.example.LargeScaleProject.Model.httpRequest.InfoRequest;
import com.example.LargeScaleProject.Model.httpRequest.LoginRequest;
import com.example.LargeScaleProject.Model.httpRequest.WatchlistAddRequest;
import com.example.LargeScaleProject.Service.*;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class  UserController{
    private final Dataservice userService;
    @Autowired
    private MongodbUserService mongodbUserService;

    @Autowired
    public UserController(Dataservice userService) {
        this.userService = userService;

    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisteredUser registrationRequest) {
        try {
            RegisteredUser savinguser= new RegisteredUser(registrationRequest.getDisplay_name(),registrationRequest.getUsername(), registrationRequest.getAge(), registrationRequest.getPassword(),registrationRequest.getCity());
            System.out.println(savinguser.get_id());
            userService.registrazioneUser(savinguser);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isfine=userService.LoginControl(loginRequest);

            if (isfine) {
                // Utente trovato e password corretta
                return new ResponseEntity<>("Login successful", HttpStatus.OK);
            } else {
                // Utente non trovato o password errata
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/info/{username}")
    public ResponseEntity <InfoRequest> infoUser(@PathVariable  String username){
        System.out.println("qui "+ username);
        InfoRequest user = userService.getInfo(username); // Assumi che hai un servizio che può trovare un utente per username
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/find")
    public ResponseEntity<InfoRequest> findUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        System.out.println("qui " + username);
        InfoRequest user = userService.getInfo(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/addToWatchlisttoWatch/{username}")
    public ResponseEntity<String> addToWatchlist(@PathVariable String  username,@RequestBody WatchlistAddRequest watchlistAddRequest) {
        try {
            System.out.println(watchlistAddRequest);
            userService.addWatchItem(username,watchlistAddRequest);
            return new ResponseEntity<>("Film added to watchlist successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/watchlisttoWatch/{username}")
    public ResponseEntity<ArrayList<WatchlistAddRequest>> getWatchlist(@PathVariable String username) {
        // Logica per ottenere gli elementi della Watchlist per l'utente
        return ResponseEntity.ok(userService.toWatchMovie(username));
    }
    @PostMapping("/histograminfo/{username}")
    public ResponseEntity<List<Long>> histograminfo(@PathVariable String username) {
        // Logica per ottenere gli elementi della Watchlist per l'utente
        return ResponseEntity.ok(userService.histograminfo(username));
    }
    @PostMapping("/watchedMovies/{username}")
    public ResponseEntity<List<WatchlistAddRequest>> getWatchedMovies(@PathVariable String username) {

            List<WatchlistAddRequest> watchedMovies = userService.WatchedMovie(username);
            return ResponseEntity.ok(watchedMovies);
        }

        @GetMapping("/expandwatched/{username}")
    public ResponseEntity<List<WatchlistAddRequest>> expandWatchedMovie(@PathVariable String username){
            List<WatchlistAddRequest> watchedMovies = userService.expandWatchedMovie(username);
            return ResponseEntity.ok(watchedMovies);

        }
    @GetMapping("/expandtowatch/{username}")
    public ResponseEntity<List<WatchlistAddRequest>> expandtoWatch(@PathVariable String username){
        List<WatchlistAddRequest> watchedMovies = userService.expandtoWatchMovie(username);
        return ResponseEntity.ok(watchedMovies);

    }
    @GetMapping("/totalRuntime/{username}")
    public ResponseEntity<Integer> getTotalRuntime(@PathVariable String username){
        Integer totalRuntime = userService.getTotalRunTime(username);
     System.out.println(totalRuntime);
            return ResponseEntity.ok(totalRuntime);

    }

}



