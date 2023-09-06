package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.Sites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Sites,Long> {
}
