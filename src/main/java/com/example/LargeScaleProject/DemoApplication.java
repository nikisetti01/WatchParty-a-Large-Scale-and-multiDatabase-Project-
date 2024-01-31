package com.example.LargeScaleProject;
import com.example.LargeScaleProject.Model.MongoDB.Admin;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbWatchlistservice;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommentService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Optional;


@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.example.LargeScaleProject.Repository.Neo4jDB")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner testUserService(MongodbUserService mongodbUserService, MongodbWatchlistservice mongodbWatchlistservice, MongodbMovieService mongodbMovieService, MongodbReviewService mongodbReviewService, Neo4jUserService neo4jUserService, Neo4jCommunityService
            neo4jCommunityService, Neo4jPostService neo4jPostService, Neo4jCommentService neo4jCommentService) {
        return args -> {


        };


        };
    }


