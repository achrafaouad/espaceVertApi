package com.example.dashborad_pipe.services;

import com.example.dashborad_pipe.Repo.EspaceVertRepo;
import com.example.dashborad_pipe.Repo.SiteRepository;
import com.example.dashborad_pipe.entities.EspaceVert;
import com.example.dashborad_pipe.entities.Sites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspaceVertService {

    private final EspaceVertRepo espaceVertRepop;
    private final SiteRepository siteRepository;

    @Autowired
    public EspaceVertService(EspaceVertRepo espaceVertRepop,
                             SiteRepository siteRepository) {
        this.espaceVertRepop = espaceVertRepop;
        this.siteRepository = siteRepository;
    }

    public EspaceVert addEspaceVert(EspaceVert espaceVert) {
        // Implement saving the espaceVert to the repository
        // You can add any additional business logic here
        return espaceVertRepop.save(espaceVert);
    }

    public List<EspaceVert> getAllEspaceVerts() {
        // Implement saving the espaceVert to the repository
        // You can add any additional business logic here
        return espaceVertRepop.findAll();
    }


    public void deleteEspaceVert(Long id_esp) {
        try{
            espaceVertRepop.delete(espaceVertRepop.findById(id_esp).get());
        }catch(Exception e ){
            throw new RuntimeException("on peux pas supprimer cet espace vert");
        }
    }

    public EspaceVert deleteSiteFromEspace(Long id, Long id_site) {
        try {
            EspaceVert espaceVert = espaceVertRepop.findById(id).orElse(null);

            if (espaceVert != null) {

                List<Sites> updatedSites = espaceVert.getSites()
                        .stream()
                        .filter(site -> !site.getId().equals(id_site))
                        .collect(Collectors.toList());

                espaceVert.setSites(updatedSites);
                siteRepository.deleteById(id_site);
                return espaceVertRepop.save(espaceVert);
            } else {
                // Handle the case where the EspaceVert with the given id is not found.
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the operation.
            e.printStackTrace(); // You might want to log the exception instead of printing it.
        }
        return null;
    }





}
