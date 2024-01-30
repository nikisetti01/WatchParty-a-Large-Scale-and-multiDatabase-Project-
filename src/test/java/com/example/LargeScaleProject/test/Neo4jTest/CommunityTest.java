package com.example.LargeScaleProject.test.Neo4jTest;

import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.UserActivity;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommunityRepository;
import com.example.LargeScaleProject.Service.Neo4jDB.Neo4jCommunityService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommunityTest {

    @Autowired
    private Neo4jCommunityService communityService;



    @Test
    @Order(1)
    void testCreateCommunity_Success() {
        // Test the service method for creating a new community
        Optional<Community> result = communityService.createCommunity(new Community("NewCommunity"));
        // Assertions
        assertTrue(result.isPresent());

    }

    @Test
    @Order(2)
    void testCreateCommunity_DuplicateName() {
        // abbiamo gia dal test precedente la newcommunity, useremo quella per verificare

        // Test the service method for creating a community with a duplicate name
        Optional<Community> result = communityService.createCommunity(new Community("NewCommunity"));

        // Assertions
        assertTrue(result.isEmpty());
    }


    @Test
    @Order(4)
    void testFindCommunityByName_Success() {
        // cercheremo la community da noi creata nel primo test

        // Test the service method
        Optional<Community> result = communityService.findCommunityByName("NewCommunity");
        // Assertions
        assertTrue(result.isPresent());

        //mostriamo di aver trovato la community
        System.out.println(result.get().getName());

        assertEquals("NewCommunity", result.get().getName());
    }

    @Test
    @Order(3)
    void testFindCommunityByName_NotFound() {
        // Test the service method for a non-existing community
        Optional<Community> result = communityService.findCommunityByName("NonExistentCommunity");

        // Assertions
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(5)
    void testDeleteCommunityById_Success() {
        //cancelleremo la community creata nel primo test
        Optional<Community> community = communityService.findCommunityByName("NewCommunity");

        // Test the service method
        boolean result = communityService.deleteCommunityById(community.get().getName());

        // Assertions
        assertTrue(result);
    }
    @Test
    @Order(6)
    void testGetMostActiveUsersInCommunity() {
        // ora useremo test su community presenti nel nostro database così da avere una visione migliore del risultato
        // Chiamare il metodo del servizio
        List<UserActivity> result = communityService.getMostActiveUsersInCommunity("Official Discussion", 10);

        System.out.println(result);
        // Verificare il risultato
        assertNotNull(result);
    }
}
