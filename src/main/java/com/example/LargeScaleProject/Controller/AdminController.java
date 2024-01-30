package com.example.LargeScaleProject.Controller;


import com.example.LargeScaleProject.Model.MongoDB.Aggregate.AgeGroupMovieCountResult;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.UserActivity;
import com.example.LargeScaleProject.Model.httpRequest.CommentRequest;
import com.example.LargeScaleProject.Model.httpRequest.LoginRequest;
import com.example.LargeScaleProject.Model.httpRequest.PostRequest;
import com.example.LargeScaleProject.Model.httpRequest.joinRequest;
import com.example.LargeScaleProject.Service.Dataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final Dataservice adminservice;

    @Autowired
    public AdminController(Dataservice adminservice) {
        this.adminservice = adminservice;
    }
    @GetMapping("/ageMovie/")
    public ResponseEntity<List<AgeGroupMovieCountResult>> getAgeMovie() {

        return ResponseEntity.ok(adminservice.ageMovie());
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isfine=adminservice.AdminLoginControl(loginRequest);

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
    @GetMapping("/activity/{name}")
    public ResponseEntity <List<UserActivity>> getAgeMovie(@PathVariable String name) {

        return ResponseEntity.ok(adminservice.getActivity(name));
    }
    @GetMapping("/deleteuser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        adminservice.deleteUser(username);

        return ResponseEntity.ok("Utente cancellato con successo");
    }
    @GetMapping("/deletemovie/{movieid}")
    public ResponseEntity<String> deleteMovie(@PathVariable String movieid){
        adminservice.deleteMovie(movieid);

        return ResponseEntity.ok("Film cancellato con successo");
    }


}
