package com.example.LargeScaleProject.Service.MongoDB;

import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Model.MongoDB.Admin;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.AgeGroupMovieCountResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.MostWatchedMovieResult;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Repository.MongoDB.AdminRepository;
import com.example.LargeScaleProject.Repository.MongoDB.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongodbUserService {

    @Autowired
    private RegisteredUserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    //aggiunge un user
    public Optional<RegisteredUser> addUser(RegisteredUser user) {
        try {
        Optional<RegisteredUser> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            System.out.println("User with the same username already exists");
            return Optional.empty();
        }
            // Controlla se l'username è già presente
            RegisteredUser savedUser = userRepository.save(user);
            return Optional.of(savedUser);

        } catch (DuplicateKeyException e) {
            throw new UserException("User with the same ID already exists");
        } catch (Exception e) {
            throw new UserException("Error while adding a user", e);
        }
    }
    public Optional<Admin> addAdmin(Admin admin){
        try {
            Optional<Admin> existingUser = adminRepository.findByUsername(admin.getUsername());
            if (existingUser.isPresent()) {
                System.out.println("User with the same username already exists");
                return Optional.empty();
            }
            // Controlla se l'username è già presente
           Admin savedUser = adminRepository.save(admin);
            return Optional.of(savedUser);

        } catch (DuplicateKeyException e) {
            throw new UserException("User with the same ID already exists");
        } catch (Exception e) {
            throw new UserException("Error while adding a user", e);
        }

    }
    public Admin findAdmin(String adminuser){
        return adminRepository.findByUsername(adminuser).get();
    }

    //elimina un user usando il suo _id
    public String deleteUser(String userId) {
        try {
            Optional<RegisteredUser> deletedUser = userRepository.findBy_id(userId);

            if (deletedUser.isPresent()) {
                userRepository.deleteBy_id(userId);
                return "User " + userId + " removed";
            } else {
                return "User " + userId + " does not exist";
            }
        } catch (Exception e) {
            throw new UserException("Error while deleting a user", e);
        }
    }
    public void update(RegisteredUser user){
        userRepository.save(user);
    }

    public Optional<RegisteredUser> findByUserName(String username) {
        try {
            System.out.println(username);
            Optional<RegisteredUser> usersFound = userRepository.findByUsername(username);

            if (usersFound.isPresent()) {
                return usersFound;
            } else {
                System.out.println("User doesn't exist");
                return Optional.empty();

            }
        } catch (UserNotFoundException unf) {
            // Gestisci specificamente l'eccezione UserNotFoundException
            System.out.println("Caught UserNotFoundException: " + unf.getMessage());
            throw unf;
        } catch (Exception e) {
            // Gestisci tutte le altre eccezioni
            System.out.println("Caught Exception: " + e.getMessage());
            throw new UserException("Error while finding a user", e);
        }
    }

    // Chiamata al sevizio per trovare quanti utenti di una citta hanno visto un film
    public Long countUsersByCityAndMovie(String city, String movieId) {
        Optional<MostWatchedMovieResult> result = userRepository.countUsersByCityAndMovie(city, movieId);
        return result.map(MostWatchedMovieResult::getUserCount).orElse(0L);
    }

    public Optional<RegisteredUser> expandWatchlistUnwatched(String username) {
        return userRepository.ExpandWatchlistUnwatched(username);
    }
    public Optional<RegisteredUser> expandWatchlistWatched(String username) {
        return userRepository.ExpandWatchlistWatched(username);
    }

    //Conta quanti film sono stati visti (watch = 1) in ciascuna fascia d'età. Può essere utile per capire quali gruppi di età sono più attivi nella visione di film.
    public List<AgeGroupMovieCountResult> countMoviesByAgeGroup() {
        return userRepository.countMoviesByAgeGroup();
    }
}


