package com.example.LargeScaleProject.Repository.MongoDB;
import com.example.LargeScaleProject.Model.MongoDB.Admin;

import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AdminRepository extends MongoRepository <Admin,String> {
    //find admin by username
    @Query("{ 'username' : ?0 }")
    Optional<Admin> findByUsername(String username);

}
