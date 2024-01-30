package com.example.LargeScaleProject.test.MongoTest;
import com.example.LargeScaleProject.Exception.UserNotFoundException;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.AgeGroupMovieCountResult;
import com.example.LargeScaleProject.Model.MongoDB.Aggregate.RatingCountResult;
import com.example.LargeScaleProject.Model.MongoDB.RegisteredUser;
import com.example.LargeScaleProject.Repository.MongoDB.RegisteredUserRepository;
import com.example.LargeScaleProject.Service.MongoDB.MongodbMovieService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbReviewService;
import com.example.LargeScaleProject.Service.MongoDB.MongodbUserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MongoUsersTest {

	@Autowired
	private MongodbMovieService movieService;
	@Autowired
	private MongodbUserService userService;
	@Autowired
	private RegisteredUserRepository userRepository;
	@Autowired
	private MongodbReviewService reviewService;

	@BeforeAll
	public static void setup(@Autowired MongodbUserService userService) {
		//inseriamo un utente test per verificare le funzionalità
		RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		if(userService.findByUserName("testUser").isPresent()){
			userService.deleteUser(userService.findByUserName("testUser").get().get_id());

		}
	}


	@Test
	void addUser() {
		RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		// When
		Optional<RegisteredUser> savedUser = userService.addUser(user);

		// Then
		assertTrue(savedUser.isPresent());
		assertEquals(user.getUsername(), savedUser.get().getUsername());
	}

	@Test
	void addUser_DuplicateUsername() {
		RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		// When
		userService.addUser(user);
		Optional<RegisteredUser> duplicateUser = userService.addUser(user);
		System.out.println(duplicateUser);

		// Then
		assertFalse(duplicateUser.isPresent());
	}

	@Test
	void deleteUser() {
		// Given
		RegisteredUser user = new RegisteredUser("Test User To Delete " , "testUserToDelete", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		RegisteredUser savedUser = userRepository.save(user);

		// When
		String result = userService.deleteUser(savedUser.get_id());

		// Then
		assertEquals("User " + savedUser.get_id() + " removed", result);
		assertFalse(userRepository.findBy_id(savedUser.get_id()).isPresent());
	}

	@Test
	void deleteUser_NonExistentUser() {
		// When
		String result = userService.deleteUser("nonExistentId");

		// Then
		assertEquals("User nonExistentId does not exist", result);
	}

	@Test
	void findByUserName() {
		RegisteredUser user = new RegisteredUser("Test User", "testUser", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		userService.addUser(user);
		// When
		Optional<RegisteredUser> foundUser = userService.findByUserName("testUser");

		// Then
		assertTrue(foundUser.isPresent());
		assertEquals(user.getUsername(), foundUser.get().getUsername());
	}

	@Test
	void findByUserName_NonExistentUser() {
		// When
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findByUserName("nonExistentUser"));

		// Then
		assertEquals("User does not exist", exception.getMessage());
	}

	@Test
	void countUsersByCityAndMovie() {

		// When
		Long userCount = userService.countUsersByCityAndMovie("Roma", "football-freaks");

		// Then
		assertEquals(23L, userCount);
	}

	@Test
	void countMoviesByAgeGroup() {
		// When
		List<AgeGroupMovieCountResult> result = userService.countMoviesByAgeGroup();
		System.out.println(result);
		// Then
		assertNotNull(result);
		// Add assertions for the expected result based on your test data
	}
	@Test
	void FindHistogramTest() {
		//esempio di stampa
		String userprovato = "sonnyc";
		List<RatingCountResult> ratingHistogram = reviewService.findRatingHistogramForUser(userprovato);
		System.out.println("Rating Histogram for User: " + userprovato);
		for (RatingCountResult result : ratingHistogram) {
			System.out.println("Rating: " + result.get_id() + ", Count: " + result.getCount());
		}
	}
	@AfterAll
	public static void cleanup(@Autowired MongodbUserService userService) {
		RegisteredUser user = new RegisteredUser("Test User To Delete " , "testUserToDelete", new String[]{"tag1", "tag2"},
				25, "password123", new ArrayList<>(), "Test City");
		userService.deleteUser(user.get_id());
	}
}
