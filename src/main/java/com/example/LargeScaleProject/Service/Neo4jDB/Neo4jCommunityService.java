package com.example.LargeScaleProject.Service.Neo4jDB;
import com.example.LargeScaleProject.Exception.CommunityException;
import com.example.LargeScaleProject.Model.Neo4jDB.Community;
import com.example.LargeScaleProject.Model.Neo4jDB.Post;
import com.example.LargeScaleProject.Model.Neo4jDB.QueryReturn.UserActivity;
import com.example.LargeScaleProject.Repository.Neo4jDB.Neo4jCommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class Neo4jCommunityService {
    @Autowired
    private final Neo4jCommunityRepository communityRepository;


    public Neo4jCommunityService(Neo4jCommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    // cerca una community per nome
    public Optional<Community> findCommunityByName(String name) {
        try {
            return communityRepository.findByName(name);
        } catch (Exception e) {
            throw new CommunityException("Errore durante la ricerca della community per nome", e);
        }
    }
    // crea una nuova community
    public Optional <Community> createCommunity(Community community) {
        try {
            // Controlla se la community con lo stesso nome esiste già
            if (communityRepository.findByName(community.getName()).isPresent()) {
                return Optional.empty();
            }


            return Optional.of(communityRepository.save(community));
        } catch (Exception e) {
            throw new CommunityException("Errore durante la creazione della community", e);
        }
    }

    // elimina una community
    public boolean deleteCommunityById(String communityId) {
        try {
            // Controlla se la community con l'ID esiste
            Optional<Community> communityOptional = communityRepository.findById(communityId);
            if (communityOptional.isEmpty()) {
                return false;
            }

            // Elimina la community
            communityRepository.deleteById(communityId);
            return true;
        } catch (Exception e) {
            throw new CommunityException("Errore durante l'eliminazione della community", e);
        }
    }
    public List<UserActivity> getMostActiveUsersInCommunity(String communityName, int limit) {
        return communityRepository.findMostActiveUsersInCommunity(communityName, limit);
    }
    public List<Post> getAllPost(String name){
        return communityRepository.findPostsByCommunityName(name);
    }

}


