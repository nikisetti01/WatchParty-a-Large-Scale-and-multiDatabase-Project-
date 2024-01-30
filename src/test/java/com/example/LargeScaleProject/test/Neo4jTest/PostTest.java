package com.example.LargeScaleProject.test.Neo4jTest;

import com.example.LargeScaleProject.Exception.PostException;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jPostRepository;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostTest {

    @Autowired
    private Neo4jPostRepository postRepository;

    @Autowired
    private Neo4jCommunityService communityService;
    @Autowired
    private Neo4jPostService postService;


    @Test
    @Order(1)
    void testCreatePost() {
        Post post = new Post("Test Register Index", "Test Text", "Test Title");
        Optional<Post> createdPost = postService.createPost(post);
        assertTrue(createdPost.isPresent());
        assertEquals(post.getPostId(), createdPost.get().getPostId());
        //puliamo il database cancellando il nuovo post
        postService.deletePost(post.getPostId());
    }

    @Test
    @Order(2)
    public void createPost_DuplicatePostId_ExceptionThrown() {

        Post existingPost = new Post("Test Register Index", "Test Text", "Test Title");

        // Arrange
        postService.createPost(existingPost); // Salva un post con lo stesso ID
        Optional<Post> result = postService.createPost(existingPost);
        assertTrue(result.isEmpty());
        //dopodiche puliamo il database
        postService.deletePost(existingPost.getPostId());
    }

    @Test
    @Order(7)
    void testDeletePost() {
        Post post = new Post("Test Register Index", "Test Text", "Test Title");
        postRepository.save(post);
        assertTrue(postService.deletePost(post.getPostId()));
        assertFalse(postRepository.findByPostId(post.getPostId()).isPresent());
    }

    @Test
    @Order(5)
    public void deletePost_PostNotFound_PostExceptionThrown() {
        // Arrange
        String nonExistingPostId = "nonExistingPostId";

        boolean result = postService.deletePost(nonExistingPostId);
        // Act & Assert
        assertEquals(false , result );
    }

    @Test
    @Order(6)
    public void deletePost_PostNotFound_ReturnsFalse() {
        // Arrange
        String nonExistingPostId = "nonExistingPostId";

        // Act
        boolean result = postService.deletePost(nonExistingPostId);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    @Order(4)
    void testBelongRelationship() {
        // Creare una community
        Optional<Community> createdCommunity = communityService.createCommunity(new Community("Test Community"));
        assertTrue(createdCommunity.isPresent());

        // Creare un post
        Post post = new Post("Test Register Index", "Test Text", "Test Title");
        Optional<Post> createdPost = postService.createPost(post);
        assertTrue(createdPost.isPresent());

        // Collegare il post alla community
        Optional<Post> result = postService.belongrelationship(post.getPostId(), createdCommunity.get().getName());
        assertTrue(result.isPresent());

        //eliminiamo i dati inseriti per pulire il database
        communityService.deleteCommunityById(createdCommunity.get().getName());
        postService.deletePost(result.get().getPostId());
    }

}
