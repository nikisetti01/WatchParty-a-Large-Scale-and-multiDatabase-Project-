package com.example.LargeScaleProject.Controller;

import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.httpRequest.CommentRequest;
import com.example.LargeScaleProject.Model.httpRequest.PostRequest;
import com.example.LargeScaleProject.Model.httpRequest.joinRequest;
import com.example.LargeScaleProject.Service.Dataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/community")
public class CommunityController {
    private final Dataservice communityservice;

    @Autowired
    public CommunityController(Dataservice communityservice) {
        this.communityservice = communityservice;
    }

    @GetMapping("/show/{name}")
    public ResponseEntity<List<PostRequest>> getCommunity(@PathVariable String name) {
        List<PostRequest> communityPosts = communityservice.getCommunityPost(name);
        return ResponseEntity.ok(communityPosts);
    }
    @PostMapping("/add-post/{communityName}")
    public ResponseEntity<PostRequest> addPost(@PathVariable String communityName, @RequestBody PostRequest postRequest) {
         communityservice.addPost(postRequest,communityName);
        return ResponseEntity.ok(postRequest);
    }
    @PostMapping("/add-comment/{titlepost}")
    public ResponseEntity<CommentRequest> addComment( @PathVariable String titlepost, @RequestBody CommentRequest commentRequest) {
        System.out.println(commentRequest+" "+ titlepost);

        communityservice.addComment(commentRequest,titlepost);
        return ResponseEntity.ok(commentRequest);
    }
    @PostMapping("/join")
    public ResponseEntity<String> joinCommunity( @RequestBody joinRequest joinRequest) {
        communityservice.joinCommunity(joinRequest);
        return ResponseEntity.ok("utente unito alla community con successo");
    }
    @GetMapping("/recommended/{username}")
    public ResponseEntity<RecommendedCommunity> getRecommendedCommunity(@PathVariable String username) {
       RecommendedCommunity recommendedCommunity = communityservice.getRecommendedCommunity(username);
        System.out.println("prova prova "+ recommendedCommunity);

        return ResponseEntity.ok(recommendedCommunity);
    }
    @GetMapping("/affinity/{username1}/{username2}")
    public ResponseEntity<String> getAffinity(@PathVariable String username1, @PathVariable String username2) {

        return ResponseEntity.ok(communityservice.affinityneo4j(username1,username2));
    }


    }


