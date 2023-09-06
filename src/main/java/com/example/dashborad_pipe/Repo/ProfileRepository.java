package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository  extends JpaRepository<Profil,Long> {
}
