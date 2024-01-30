package com.example.LargeScaleProject.test.Neo4jTest;

import com.example.LargeScaleProject.Model.Neo4jDB.Comment;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommentRepository;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommentService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {

    @Autowired
    private Neo4jCommentService commentService;

    @Autowired
    private Neo4jUserService userService;

    @Autowired
    private Neo4jPostService postService;

    @Autowired
    private Neo4jCommentRepository commentRepository;

    private Post testPost;
    private User testUser;




    @Test
    @Order(1)
    public void testCreateComment() {
        // Creazione di un post e di un utente di test
        testPost = new Post("Test Register Index", "Test Text", "Test Title");
        postService.createPost(testPost);

        testUser = new User("TestUsername", "Test Display Name");
        userService.createUser(testUser);
        // Creazione di un commento
        Comment comment = new Comment("Test Comment");
        Optional<Comment> createdComment =  commentService.createComment(comment , testUser.getUsername(), testPost.getPostId());

        // Verifica che il commento sia stato creato correttamente
        assertTrue(createdComment.isPresent());
        assertEquals("Test Comment", createdComment.get().getText());
        //puliamo il database
        postService.deletePost(testPost.getPostId());
        userService.deleteUser(testUser.getId());

    }


    @Test
    @Order(2)
    public void testPostComments() {
       //inizializziamo user e post
        testPost = new Post("Test Register Index", "Test Text", "Test Title");
        postService.createPost(testPost);

        testUser = new User("TestUsername", "Test Display Name");
        userService.createUser(testUser);

        // Creazione di due commenti per il post di test
        Comment comment1 = new Comment("Test Comment 1");
        Comment comment2 = new Comment("Test Comment 2");
        Comment comment3 = new Comment("Test Comment 3");
        commentService.createComment(comment1 , testUser.getUsername(), testPost.getPostId());
        commentService.createComment(comment2 , testUser.getUsername(), testPost.getPostId());
        commentService.createComment(comment3 , testUser.getUsername(), testPost.getPostId());

        // Recupero dei commenti associati al post di test
        List<Comment> postComments = commentService.Postcomments(testPost.getPostId());
        System.out.println(postComments);

        // Verifica che i commenti siano stati recuperati correttamente
        assertEquals(3, postComments.size());
        assertTrue(postComments.stream().anyMatch(c -> c.getText().equals("Test Comment 1")));
        assertTrue(postComments.stream().anyMatch(c -> c.getText().equals("Test Comment 2")));
        //puliamo il database
        postService.deletePost(testPost.getPostId());
        userService.deleteUser(testUser.getId());
    }
}
