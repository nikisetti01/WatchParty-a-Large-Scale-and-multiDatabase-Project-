package com.example.LargeScaleProject.Service.Neo4jDB;

import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommentRepository;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class Neo4jUserService {
    @Autowired
    private final Neo4jUserRepository userRepository;
    @Autowired
    Neo4jCommunityService neo4jCommunityService;
    @Autowired
    public Neo4jPostService neo4jPostService;
    @Autowired
    public Neo4jCommentRepository neo4jCommentRepository;

    // In Neo4j bisogna inizializzare la classe con il costruttore per Service
    public Neo4jUserService(Neo4jUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Creazione di un nodo -> nuovo Utente
    public Optional<User> createUser(User user) {
        try {
            // controllo che nessun utente abbia quell'username
            if (this.getUserByUsername(user.getUsername()).isPresent()) {
                System.out.print("Ho trovato l'utente nel db");
                return Optional.empty();
            } else
                return Optional.of(userRepository.save(user));
        } catch (Exception e) {
            throw new UserException("Errore nella creazione nodo User", e);
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    // cancella utente da Id
    public boolean deleteUser(Long userId) {
        try {
            //controllo che l'id sia esistente
            if (userRepository.findById(userId).isEmpty())
                return false;
            else {
                userRepository.deleteById(userId);
                return true;
            }
        } catch (Exception e) {
            throw new UserException("Errore durante l'eliminazione dell'utente", e);
        }
    }
    public boolean deleteUserbyusername(String username) {
        try {
            //controllo che l'id sia esistente
            if (userRepository.findByUsername(username).isEmpty())
                return false;
            else {
                userRepository.deleteByUsername(username);
                return true;
            }
        } catch (Exception e) {
            throw new UserException("Errore durante l'eliminazione dell'utente", e);
        }
    }

    public Optional<User> getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new UserException("Errore durante il recupero dell'utente per username", e);
        }
    }

    public Optional<User> joinCommunity(String username, String communityName) {
        try {
            Optional<User> userOptional = getUserByUsername(username);
            Optional<Community> communityOptional = neo4jCommunityService.findCommunityByName(communityName);

            if (userOptional.isPresent() && communityOptional.isPresent()) {
                User user = userOptional.get();
                userRepository.joinCommunity(username, communityName);
                user.joinCommunity(communityOptional.get());
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new UserException("Errore durante la connessione dell'utente alla community", e);
        }
    }

    public List<Community> findAllCommunitiesByUsername(String username) {
        return userRepository.findAllCommunitiesByUsername(username);
    }

    public Optional<User> postedrelation(String username, String postid) {
        try {

            Optional<User> userOptional = this.getUserByUsername(username);
            Optional<Post> postOptional = neo4jPostService.findById(postid);

            if (userOptional.isPresent() && postOptional.isPresent()) {

                Community communitypost = neo4jPostService.findCommunity(postOptional.get().getPostId()).get();
                List<Community> communityuser = this.findAllCommunitiesByUsername(userOptional.get().getUsername());
                Optional<String> userpost = neo4jPostService.findAuthorbypost(postOptional.get().getPostId());
                if ( userpost.isEmpty()) {
                    User user = userOptional.get();
                    userRepository.postInUser(username, postid);
                    return Optional.of(user);
                }
            }
            return Optional.empty(); // Restituisci Optional.empty() solo se il controllo precedente fallisce
        } catch (Exception e) {
            throw new UserException("Error connecting user to a Post", e);
        }
    }

    public List<Post> findAllPostByUser(String username) {
        List<Post> posts = userRepository.findAllPostsByUsername(username);
        return posts;
    }

    //raccomanda una community ad un utente
    public RecommendedCommunity recommendaComunity(String username) {
        return userRepository.getMostActiveUserAndCommunity(username);
    }

    public Integer pathdistance(String username1, String username2) {
        Optional<User> user1 = this.getUserByUsername(username1);
        Optional<User> user2 = this.getUserByUsername(username1);
        if ((user1.isPresent() && user2.isPresent()) && (!Objects.equals(user1.get(), user2.get())))
            return userRepository.findShortestPath(username1, username2);
        else
            return null;
    }

}
