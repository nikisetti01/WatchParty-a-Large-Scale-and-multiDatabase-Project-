package com.example.LargeScaleProject.test.Neo4jTest;

import com.example.LargeScaleProject.Exception.UserException;
import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.RecommendedCommunity;
import com.example.LargeScaleProject.Model.Neo4jDB.User;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jUserRepository;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jPostService;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jUserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Neo4jUserTest {

    @Autowired
    private Neo4jUserRepository userRepository;

    @Autowired
    private Neo4jUserService userService;
    @Autowired
    private Neo4jCommunityService communityService;
    @Autowired
    private Neo4jPostService postService;

    @Test
    @Order(1)
    void createUser_Success() {
        // Arrange
        User user = new User("testUsername", "Test Display Name");

        // Act
        Optional<User> createdUser = userService.createUser(user);
        // Assert
        System.out.println(createdUser);
        assertTrue(createdUser.isPresent());
        assertEquals(user.getUsername(), createdUser.get().getUsername());
        assertEquals(user.getDisplay_name(), createdUser.get().getDisplay_name());

        // Clean up: delete the created user from the database
        userService.deleteUser(user.getId());

    }

    @Test
    @Order(2)
    void createUser_UsernameAlreadyExists() {
        // Arrange
        User existingUser = new User("existingUser", "Existing User");
        Optional<User> createdUser = userService.createUser(existingUser);

        User newUser = new User("existingUser", "New User");

        // Act
        Optional<User> result = userService.createUser(newUser);

        // Assert
        assertTrue(result.isEmpty());
        userRepository.deleteById(existingUser.getId());
    }

    @Test
    @Order(6)
    void deleteUser_Success() {
        // Arrange
        User user = new User("existingUser", "Test Display Name");
        Optional<User> createdUser = userService.createUser(user);

        // Act
        boolean result = userService.deleteUser(createdUser.get().getId());

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(5)
    void deleteUser_UserDoesNotExist() {
        // Act
        boolean result = userService.deleteUser(999L);

        // Assert
        assertFalse(result);
    }
    @Test
    @Order(4)
    public void getUserByUsername_Success() {
        // Arrange
        User user = new User("testUsername", "Test Display Name");
        Optional<User> createdUser = userService.createUser(user);

        // Act
        Optional<User> result = userService.getUserByUsername(createdUser.get().getUsername());
        // Assert
        assertTrue(result.isPresent());
        assertEquals(createdUser.get().getDisplay_name(), result.get().getDisplay_name());
        userRepository.deleteById(createdUser.get().getId());
    }

    @Test
    @Order(3)
    public void getUserByUsername_NotExistingUser() {
        // Arrange
        String username = "notExistingUser";
        Optional <User> userOptional = userService.getUserByUsername(username);
        assertTrue(userOptional.isEmpty());

    }
    @Test
    @Order(8)
    public void testJoinCommunity_Success() {
        // Creazione di un utente e di una community di test nel database
        User user = new User("testUser", "Test Display Name");
        userRepository.save(user);

        Community community = new Community("testCommunity");
        communityService.createCommunity(community);

        // Invocazione del metodo joinCommunity
        Optional<User> result = userService.joinCommunity("testUser", "testCommunity");

        // Verifica che l'utente sia stato correttamente aggiunto alla community
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        //prendiamo la lista delle community dell'username, che ora essendo un nuovo user con una sola community ci darà il risultato
        List<Community> communities = userService.findAllCommunitiesByUsername(result.get().getUsername());
        //stampiamo la lista delle communites per dimostrarlo
        System.out.println(communities);
        assertEquals("testCommunity" , communities.get(0).getName());
        //pulizia db
        userService.deleteUser(user.getId());
        communityService.deleteCommunityById(community.getName());
    }
    @Test
    @Order(7)
    public void testJoinCommunity_NotExistingCommunity() {

        User user = new User("testUser", "Test Display Name");
        userRepository.save(user);

        Optional<User> result = userService.joinCommunity("testUser", "NotExistingCommunity");
        assertEquals(Optional.empty() , result);

        //pulizia db
        userService.deleteUser(user.getId());
    }

    @Test
    @Order(9)
    void testRecommendaCommunity() {
        // Useremo un utente predefinito del database per mostrare un output effettivo
        String username = "vinsim27";
        // Chiamata al metodo
        RecommendedCommunity recommendedCommunity = userService.recommendaComunity(username);
        //stampiamo il risultato
        System.out.println(recommendedCommunity);
        // Verifica
        assertNotNull(recommendedCommunity);

    }
    @Test
    @Order(10)
    public void testFindAllPostByUser() {
        // / Useremo un utente predefinito del database per mostrare un output effettivo
        String username = "worg";

        // Eseguiamo il metodo
        List<Post> posts = userService.findAllPostByUser(username);

        // Verifichiamo che la lista dei post non sia nulla
        assertNotNull(posts);

        //verifichiamo che tutti i post siano del nostro utente
        for (Post post : posts) {
            assertEquals(username, postService.findAuthorbypost(post.getPostId()).get());
        }
    }

    @Test
    @Order(11)
    public void testPostedRelation() {
        // Simuliamo l'esistenza di un utente e di un post in una community
        User user = new User("testUser");
        Post post = new Post("Test Register Index", "Test Text", "Test Title");
        Community community = new Community("TestCommunity");
        communityService.createCommunity(community);
        userService.createUser(user);
        postService.createPost(post);

        //collego il post all community e l'user alla community
        post = postService.belongrelationship(post.getPostId(), community.getName()).get();
        user =  userService.joinCommunity(user.getUsername() , community.getName()).get();

        // Testa la funzione postedrelation collegando il post all'utente
        Optional<User> result = userService.postedrelation(user.getUsername(), post.getPostId());

        // Verifica che la relazione sia stata stabilita correttamente
        assertTrue(result.isPresent());
        assertEquals(user.getUsername() , postService.findAuthorbypost(post.getPostId()).get());

        //pulizia del db
        postService.deletePost(post.getPostId());
        userService.deleteUser(user.getId());
        communityService.deleteCommunityById(community.getName());
    }
}
